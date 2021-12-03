package com.bas.google_book_app.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    open fun loadAllBooks(): LiveData<MutableList<BookEntry?>?>?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    open fun insertBook(bookEntry: BookEntry?)
    @Delete
    open fun deleteBook(bookEntry: BookEntry?)
    @Query("SELECT * FROM book WHERE book_id = :bookId")
    open fun loadBookById(bookId: String?): LiveData<BookEntry?>?
}