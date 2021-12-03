package com.bas.google_book_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bas.google_book_app.db.BookDatabase
import com.bas.google_book_app.utilsdata.Constants
import com.bas.google_book_app.utilsdata.Constants.DATABASE_NAME
import timber.log.Timber

@Database(entities = [BookEntry::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    companion object {

        private val LOCK = Any()
        private var sInstance: BookDatabase? = null
        fun getInstance(context: Context): BookDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    Timber.d("Creating new Room DB instance")
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        BookDatabase::class.java, DATABASE_NAME.toString()
                    )
                        .build()
                }
            }
            Timber.d("Getting Room DB instance")
            return sInstance
        }
    }

    abstract fun bookDao(): BookDao?
}