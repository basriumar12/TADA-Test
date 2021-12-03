package com.bas.google_book_app.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bas.google_book_app.domain.Book

class BookDataSourceFactory : DataSource.Factory<Int?, Book?>() {
    private var mPostLiveData: MutableLiveData<BookDataSource?>?
    private var mBookDataSource: BookDataSource? = null
    override fun create(): DataSource<Int?, Book?> {
        mBookDataSource = BookDataSource()
        mPostLiveData = MutableLiveData()
        mPostLiveData?.postValue(mBookDataSource)
        return mBookDataSource as BookDataSource
    }

    fun getPostLiveData(): MutableLiveData<BookDataSource?>? {
        return mPostLiveData
    }

    init {
        mPostLiveData = MutableLiveData()
    }
}