package com.bas.google_book_app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bas.google_book_app.db.BookEntry
import com.bas.google_book_app.repository.BookRepository
import com.bas.google_book_app.utilsdata.AppThreadExecutors

class DetailViewModel(private val mRepository: BookRepository?, bookId: String?) : ViewModel() {
    private val mBookEntry: LiveData<BookEntry?>?
    fun getBookEntry(): LiveData<BookEntry?>? {
        return mBookEntry
    }

    fun addFavoriteBook(book: BookEntry?) {
        AppThreadExecutors.Companion.getInstance()?.diskIO()
            ?.execute(Runnable { mRepository?.addFavoriteBook(book) })
    }

    fun removeFavoriteBook(book: BookEntry?) {
        AppThreadExecutors.Companion.getInstance()?.diskIO()
            ?.execute(Runnable { mRepository?.removeFavoriteBook(book) })
    }

    init {
        mBookEntry = mRepository?.getFavoriteBookById(bookId)
    }
}