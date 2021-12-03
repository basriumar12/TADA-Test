package com.bas.google_book_app.repository

import androidx.lifecycle.LiveData
import com.bas.google_book_app.db.BookDao
import com.bas.google_book_app.db.BookEntry
import timber.log.Timber

class BookRepository private constructor(private val mBookDao: BookDao?) {
    fun getFavoriteBooks(): LiveData<MutableList<BookEntry?>?>? {
        return mBookDao?.loadAllBooks()
    }

    fun getFavoriteBookById(bookId: String?): LiveData<BookEntry?>? {
        return mBookDao?.loadBookById(bookId)
    }

    fun addFavoriteBook(book: BookEntry?) {
        mBookDao?.insertBook(book)
    }

    fun removeFavoriteBook(book: BookEntry?) {
        mBookDao?.deleteBook(book)
    }

    companion object {
        private val LOCK: Any? = Any()
        private var sInstance: BookRepository? = null
        @Synchronized
        fun getInstance(
            bookDao: BookDao?
        ): BookRepository? {
            Timber.d("Getting repository")
            if (sInstance == null) {
                if (LOCK != null) {
                    synchronized(LOCK) {
                        Timber.d("Creating new repository")
                        sInstance = BookRepository(bookDao)
                    }
                }
            }
            return sInstance
        }
    }
}