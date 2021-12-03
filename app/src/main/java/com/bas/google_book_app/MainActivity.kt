package com.bas.google_book_app

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.bas.google_book_app.MainActivity
import com.bas.google_book_app.databinding.ActivityMainBinding
import com.bas.google_book_app.db.BookEntry
import com.bas.google_book_app.db.BookPreferences
import com.bas.google_book_app.domain.Book
import com.bas.google_book_app.ui.SpacingItemDecoration
import com.bas.google_book_app.ui.detail.DetailActivity
import com.bas.google_book_app.ui.main.BookPagedListAdapter
import com.bas.google_book_app.ui.main.BookPagedListAdapter.BookPagedListAdapterOnClickHandler
import com.bas.google_book_app.ui.main.FavoriteBookAdapter
import com.bas.google_book_app.ui.main.FavoriteBookAdapter.FavoriteOnClickHandler
import com.bas.google_book_app.ui.main.MainActivityViewModel
import com.bas.google_book_app.ui.profil.ProfilActivity
import com.bas.google_book_app.ui.settings.SettingsActivity
import com.bas.google_book_app.utilsdata.Constants
import com.bas.google_book_app.utilsdata.DependenciesUtils
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity(), FavoriteOnClickHandler,
    BookPagedListAdapterOnClickHandler, OnSharedPreferenceChangeListener {
    private var mMainViewModel: MainActivityViewModel? = null
    private var mFilteredBy: String? = null
    private var mMainBinding: ActivityMainBinding? = null
    private var mBookPagedListAdapter: BookPagedListAdapter? = null
    private var mFavoriteBookAdapter: FavoriteBookAdapter? = null
    private var mSavedLayoutState: Parcelable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState == null) {
            showNetworkDialog(isOnline())
        }
        setNav()
        initAdapter()
        mFilteredBy = BookPreferences.getFilterPreference(this)
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
        createViewModel(mFilteredBy)
        refreshUI()
        setSwipeRefreshListener()
        customizeGridColumns()
        if (savedInstanceState != null) {
            mSavedLayoutState = savedInstanceState.getParcelable(Constants.LAYOUT_MANAGER_STATE)
            Objects.requireNonNull(mMainBinding?.rvBook?.layoutManager)
                ?.onRestoreInstanceState(mSavedLayoutState)
        }
    }

    private fun setNav() {}
    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == getString(R.string.pref_key_filter_by)) {
            mFilteredBy =
                sharedPreferences?.getString(key, getString(R.string.pref_filter_by_default))
        }
        mMainViewModel?.setBookPagedList(mFilteredBy)
        refreshUI()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.getItemId()
        return when (id) {
            R.id.action_filter -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_profil -> {
                val intentTo = Intent(this@MainActivity, ProfilActivity::class.java)
                startActivity(intentTo)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(
            Constants.LAYOUT_MANAGER_STATE,
            mMainBinding?.rvBook?.layoutManager?.onSaveInstanceState()
        )
    }

    private fun initAdapter() {
        val layoutManager = GridLayoutManager(this, Constants.GRID_SPAN_COUNT)
        mMainBinding?.rvBook?.layoutManager = layoutManager
        mMainBinding?.rvBook?.setHasFixedSize(true)
        mBookPagedListAdapter = BookPagedListAdapter(this)
        mFavoriteBookAdapter = FavoriteBookAdapter(this, this)
    }

    private fun customizeGridColumns() {
        val decoration = SpacingItemDecoration(
            Constants.GRID_SPAN_COUNT, Constants.GRID_SPACING,
            Constants.GRID_INCLUDE_EDGE
        )
        mMainBinding?.rvBook?.addItemDecoration(decoration)
    }

    private fun setSwipeRefreshListener() {
        mMainBinding?.swipeRefresh?.setOnRefreshListener {
            showDataView()
            refreshUI()
            mMainBinding?.swipeRefresh?.isRefreshing = false
            showSnackbarRefresh(isOnline())
        }
    }

    private fun showSnackbarRefresh(isOnline: Boolean) {
        if (isOnline) {
            mMainBinding?.rvBook?.let {
                Snackbar.make(
                    it, getString(R.string.snackbar_updated), Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun refreshUI() {
        mMainViewModel?.setFavoriteBooks()
        if (mFilteredBy == getString(R.string.pref_filter_by_favorites)) {
            mMainBinding?.rvBook?.adapter = mFavoriteBookAdapter
            observeFavorites()
        } else {
            mMainBinding?.rvBook?.adapter = mBookPagedListAdapter
            observePagedList()
        }
    }

    private fun observePagedList() {
        mMainViewModel?.getBookPagedList()?.observe(this, { pagedList ->
            showDataView()
            if (pagedList != null) {
                mBookPagedListAdapter?.submitList(pagedList)
                Timber.e("List size: %d", pagedList.size)
                Objects.requireNonNull(mMainBinding?.rvBook?.layoutManager)
                    ?.onRestoreInstanceState(mSavedLayoutState)
            }
            if (!isOnline()) {
                showDataView()
                showOfflineMessage()
            }
        })
    }

    private fun showOfflineMessage() {
        val snackbar = mMainBinding?.frameMain?.let {
            Snackbar.make(
                it, R.string.snackbar_offline, Snackbar.LENGTH_LONG
            )
        }
        val sbView = snackbar?.view
        sbView?.setBackgroundColor(Color.WHITE)
        val textView =
            sbView?.findViewById<TextView?>(com.google.android.material.R.id.snackbar_text)
        textView?.setTextColor(Color.BLACK)
        snackbar?.show()
    }

    private fun observeFavorites() {
        mMainViewModel?.getFavoriteBooks()?.observe(this, { favoriteBooks ->
            mFavoriteBookAdapter?.setBooks(favoriteBooks)
            mMainBinding?.rvBook?.layoutManager?.onRestoreInstanceState(mSavedLayoutState)
            if (favoriteBooks == null || favoriteBooks.size == 0) {
                showEmptyView()
            } else if (!isOnline()) {
                showDataView()
            }
        })
    }

    private fun showDataView() {
        mMainBinding?.tvEmpty?.visibility = View.INVISIBLE
        mMainBinding?.rvBook?.visibility = View.VISIBLE
    }

    private fun showEmptyView() {
        mMainBinding?.tvEmpty?.visibility = View.VISIBLE
        mMainBinding?.tvEmpty?.text = getString(R.string.empty_favorites_message)
        mMainBinding?.tvEmpty?.setCompoundDrawablesWithIntrinsicBounds(
            0,
            R.drawable.ic_baseline_menu_book_24, 0, 0
        )
        mMainBinding?.tvEmpty?.setTextColor(Color.WHITE)
    }

    private fun isOnline(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun showNetworkDialog(isOnline: Boolean) {
        if (!isOnline) {
            val builder = AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
            builder.setTitle(getString(R.string.no_network_title))
            builder.setMessage(getString(R.string.no_network_message))
            builder.setPositiveButton(getString(R.string.go_to_settings)) { dialog, which ->
                startActivityForResult(
                    Intent(
                        Settings.ACTION_SETTINGS
                    ), Constants.REQUEST_CODE_DIALOG
                )
            }
            builder.setNegativeButton(getString(R.string.cancel), null)
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun createViewModel(filterBy: String?) {
        val factory = DependenciesUtils.provideMainViewModelFactory(
            this@MainActivity, filterBy
        )
        mMainViewModel = factory?.let {
            ViewModelProvider(this, it).get(
                MainActivityViewModel::class.java
            )
        }
    }

    override fun onFavItemClick(favEntry: BookEntry?) {
        val id = favEntry?.getBookId()
        val title = favEntry?.getTitle()
        val subtitle = favEntry?.getSubtitle()
        val authors = favEntry?.getAuthors()
        val description = favEntry?.getDescription()
        val buyLink = favEntry?.getBuyLink()
        val thumbnailURL = favEntry?.getThumbnailURL()
        val book = Book(
            id, title, subtitle, authors?.split("\n".toRegex())?.toTypedArray(), description, buyLink,
            thumbnailURL
        )
        val bundle = Bundle()
        bundle.putParcelable(Constants.EXTRA_BOOK, book)
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(Constants.EXTRA_BOOK, bundle)
        startActivity(intent)
    }

    override fun onItemClick(book: Book?) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.EXTRA_BOOK, book)
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(Constants.EXTRA_BOOK, bundle)
        startActivity(intent)
    }
}