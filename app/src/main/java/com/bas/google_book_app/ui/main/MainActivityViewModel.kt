package com.bas.google_book_app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bas.google_book_app.db.BookEntry
import com.bas.google_book_app.domain.Book
import com.bas.google_book_app.repository.BookDataSourceFactory
import com.bas.google_book_app.repository.BookRepository
import com.bas.google_book_app.utilsdata.AppThreadExecutors
import com.bas.google_book_app.utilsdata.Constants

class MainActivityViewModel(
    private val mFilterListBy: String?,
    private val mRepository: BookRepository?
) : ViewModel() {
    private var mBookPagedList: LiveData<PagedList<Book?>?>? = null
    private var mFavoriteBooks: LiveData<MutableList<BookEntry?>?>? = null
    private fun init(mFilterListBy: String?) {
        val bookDataFactory = BookDataSourceFactory()
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(Constants.INITIAL_LOAD_SIZE_HINT)
            .setPageSize(Constants.PAGE_SIZE)
            .setPrefetchDistance(Constants.PREFETCH_DISTANCE)
            .build()
        mBookPagedList = AppThreadExecutors.Companion.getInstance()?.networkIO()?.let {
            LivePagedListBuilder(bookDataFactory, config)
                .setFetchExecutor(it)
                .build()
        }
    }

    fun getBookPagedList(): LiveData<PagedList<Book?>?>? {
        return mBookPagedList
    }

    fun setBookPagedList(filterBy: String?) {
        init(mFilterListBy)
    }

    fun getFavoriteBooks(): LiveData<MutableList<BookEntry?>?>? {
        return mFavoriteBooks
    }

    fun setFavoriteBooks() {
        mFavoriteBooks = mRepository?.getFavoriteBooks()
    }

    init {
        init(mFilterListBy)
    }
}