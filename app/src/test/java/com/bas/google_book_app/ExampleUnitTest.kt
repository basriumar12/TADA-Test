package com.bas.google_book_app

import com.bas.google_book_app.domain.dto.BookImageLinksDTO.thumbnail
import androidx.room.Dao
import androidx.lifecycle.LiveData
import com.bas.google_book_app.db.BookEntry
import androidx.room.OnConflictStrategy
import androidx.room.Delete
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.RoomDatabase
import com.bas.google_book_app.db.BookDao
import com.bas.google_book_app.db.BookDatabase
import timber.log.Timber
import androidx.room.Room
import android.content.SharedPreferences
import com.bas.google_book_app.R
import com.bas.google_book_app.ui.main.FavoriteBookAdapter.FavoriteOnClickHandler
import androidx.recyclerview.widget.RecyclerView
import com.bas.google_book_app.ui.main.FavoriteBookAdapter.FavoriteViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import com.bas.google_book_app.ui.main.BookPagedListAdapter.BookPagedListAdapterOnClickHandler
import androidx.paging.PagedListAdapter
import com.bas.google_book_app.ui.main.BookPagedListAdapter.BookPagedViewHolder
import com.bas.google_book_app.ui.main.BookPagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bas.google_book_app.repository.BookRepository
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.lifecycle.ViewModel
import com.bas.google_book_app.ui.main.MainActivityViewModel
import androidx.paging.PagedList
import com.bas.google_book_app.repository.BookDataSourceFactory
import androidx.paging.LivePagedListBuilder
import com.bas.google_book_app.utilsdata.AppThreadExecutors
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.bas.google_book_app.ui.register.RegisterActivity
import android.widget.Toast
import com.bas.google_book_app.utilsdata.UserSession
import com.bas.google_book_app.MainActivity
import com.bas.google_book_app.ui.detail.DetailViewModel
import android.view.MenuInflater
import com.bas.google_book_app.ui.detail.DetailViewModelFactory
import com.bas.google_book_app.utilsdata.DependenciesUtils
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import android.widget.TextView
import com.bas.google_book_app.ui.login.LoginActivity
import androidx.core.app.NavUtils
import androidx.preference.PreferenceFragmentCompat
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.bas.google_book_app.ui.settings.SettingsFragment
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.appcompat.widget.AppCompatImageView
import android.view.View.MeasureSpec
import com.bas.google_book_app.domain.dto.BookVolumeInfoDTO
import com.bas.google_book_app.domain.dto.BookSaleInfoDTO
import com.google.gson.annotations.SerializedName
import com.bas.google_book_app.domain.dto.BookDTO
import com.bas.google_book_app.domain.dto.BookImageLinksDTO
import android.os.Parcelable
import android.os.Parcel
import android.text.TextUtils
import com.bas.google_book_app.ui.main.MainViewModelFactory
import android.os.Looper
import androidx.paging.PageKeyedDataSource
import com.bas.google_book_app.network_service.GoogleBookAPI
import com.bas.google_book_app.domain.dto.BookResponseDTO
import com.bas.google_book_app.network_service.RetrofitServiceController
import kotlin.jvm.Synchronized
import androidx.lifecycle.MutableLiveData
import com.bas.google_book_app.repository.BookDataSource
import timber.log.Timber.DebugTree
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.bas.google_book_app.ui.main.FavoriteBookAdapter
import com.bas.google_book_app.db.BookPreferences
import com.bas.google_book_app.ui.settings.SettingsActivity
import com.bas.google_book_app.ui.profil.ProfilActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bas.google_book_app.ui.SpacingItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.bas.google_book_app.ui.detail.DetailActivity
import org.junit.runner.RunWith
import org.junit.rules.TestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.mockito.Mock
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import kotlin.Throws
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, (2 + 2).toLong())
    }
}