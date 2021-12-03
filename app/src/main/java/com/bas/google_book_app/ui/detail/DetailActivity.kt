package com.bas.google_book_app.ui.detail

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bas.google_book_app.R
import com.bas.google_book_app.databinding.ActivityDetailBinding
import com.bas.google_book_app.db.BookDatabase
import com.bas.google_book_app.db.BookEntry
import com.bas.google_book_app.domain.Book
import com.bas.google_book_app.ui.detail.DetailActivity
import com.bas.google_book_app.utilsdata.Constants
import com.bas.google_book_app.utilsdata.DependenciesUtils
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber

class DetailActivity : AppCompatActivity() {
    private var mDetailBinding: ActivityDetailBinding? = null
    private var mBook: Book? = null
    private var mIsFavorite = false
    private var mDetailViewModel: DetailViewModel? = null
    private var mBookDB: BookDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        mBookDB = BookDatabase.getInstance(this)
        val intent = intent
        if (intent != null) {
            if (intent.hasExtra(Constants.EXTRA_BOOK)) {
                val b = intent.getBundleExtra(Constants.EXTRA_BOOK)
                if (b != null) {
                    mBook = b.getParcelable(Constants.EXTRA_BOOK)
                } else {
                    Timber.e("Extra Bundle has no book!")
                }
            }
        }
        createViewModel()
        observeBookEntry()
        refreshUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (mBook?.mBuyLink != null) {
            val inflater = menuInflater
            inflater.inflate(R.menu.detail, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item?.getItemId()
        if (itemId == R.id.action_buy) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(mBook?.mBuyLink)
            startActivity(i)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createViewModel() {
        val factory = DependenciesUtils.provideDetailViewModelFactory(
            this@DetailActivity, mBook?.mId
        )
        mDetailViewModel = factory?.let {
            ViewModelProvider(this,
                it
            ).get(DetailViewModel::class.java)
        }
    }

    private fun observeBookEntry() {
        mDetailViewModel?.getBookEntry()?.observe(this, { bookEntry: BookEntry? ->
            mIsFavorite = if (mDetailViewModel?.getBookEntry()?.value == null) {
                mDetailBinding?.fabFavorite?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                false
            } else {
                mDetailBinding?.fabFavorite?.setImageResource(R.drawable.ic_baseline_favorite_24)
                true
            }
        })
    }

    fun onFavoriteClick(view: View?) {
        val bookEntry = convertToBookEntry(mBook)
        if (!mIsFavorite) {
            mDetailViewModel?.addFavoriteBook(bookEntry)
            showSnackbarMessage(R.string.snackbar_added)
        } else {
            val bookEntry2 = mDetailViewModel?.getBookEntry()?.value
            mDetailViewModel?.removeFavoriteBook(bookEntry2)
            showSnackbarMessage(R.string.snackbar_removed)
        }
        mDetailViewModel?.getBookEntry()?.value
    }

    private fun convertToBookEntry(book: Book?): BookEntry? {
        return BookEntry(
            book?.mId, book?.mTitle, book?.mSubtitle, book?.mAuthors.toString(),
            book?.mDescription, book?.mBuyLink, book?.mThumbnailURL
        )
    }

    private fun refreshUI() {
        showUpButton()
        setCollapsingToolbarTitle()
        loadBookImage()
        setTextLabels()
    }

    private fun showUpButton() {
        setSupportActionBar(mDetailBinding?.toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }

    private fun setCollapsingToolbarTitle() {
        mDetailBinding?.appBarLayout?.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout?.getTotalScrollRange()!!
                }
                if (scrollRange + verticalOffset == 0) {
                    mDetailBinding?.collapsingToolbarLayout?.title = mBook?.mTitle
                    isShow = true
                } else if (isShow) {
                    mDetailBinding?.collapsingToolbarLayout?.title = " "
                    isShow = false
                }
            }
        })
    }

    private fun loadBookImage() {
        var thumbnail = mBook?.mThumbnailURL
        thumbnail = thumbnail?.replaceFirst("^(http://)?(www\\.)?".toRegex(), "https://")
        Picasso.with(this)
            .load(thumbnail)
            .error(R.drawable.ic_baseline_broken_image_24)
            .into(mDetailBinding?.ivThumbnail)
    }

    private fun setTextLabels() {
        setTitleLabels()
        setSubtitleLabels()
        setDescription()
        setAuthorLabels()
    }

    private fun setAuthorLabels() {
        val author = mBook?.mAuthors
        mDetailBinding?.tvAuthorText?.text = author.toString()
    }

    private fun setDescription() {
        val description = mBook?.mDescription
        mDetailBinding?.tvDescriptionText?.text = description.toString()
    }

    private fun setTitleLabels() {
        val title = mBook?.mTitle
        mDetailBinding?.tvDetailTitle?.text = title.toString()
    }

    private fun setSubtitleLabels() {
        val subtitle = mBook?.mSubtitle.toString()
        if (subtitle != null) {
            mDetailBinding?.tvDetailSubtitle?.text = subtitle
        }
    }

    private fun showSnackbarMessage(resourceMessageID: Int) {
        val snackbar = mDetailBinding?.coordinator?.let {
            Snackbar.make(
                it, resourceMessageID, Snackbar.LENGTH_SHORT
            )
        }
        val sbView = snackbar?.view
        sbView?.setBackgroundColor(Color.DKGRAY)
        val textView =
            sbView?.findViewById<TextView?>(com.google.android.material.R.id.snackbar_text)
        textView?.setTextColor(Color.WHITE)
        snackbar?.show()
    }
}