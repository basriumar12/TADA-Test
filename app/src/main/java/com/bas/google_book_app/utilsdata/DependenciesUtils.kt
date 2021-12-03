package com.bas.google_book_app.utilsdata

import android.content.Context
import com.bas.google_book_app.db.BookDatabase
import com.bas.google_book_app.repository.BookRepository
import com.bas.google_book_app.ui.detail.DetailViewModelFactory
import com.bas.google_book_app.ui.main.MainViewModelFactory

object DependenciesUtils {
    fun provideMainViewModelFactory(context: Context, filterBy: String?): MainViewModelFactory? {
        val repository = getRepository(context.applicationContext)
        return MainViewModelFactory(filterBy, repository)
    }

    fun provideDetailViewModelFactory(context: Context, bookId: String?): DetailViewModelFactory? {
        val repository = getRepository(context.applicationContext)
        return DetailViewModelFactory(repository, bookId)
    }

    fun getRepository(context: Context): BookRepository? {

        val database: BookDatabase? = BookDatabase.getInstance(context.applicationContext)
        return BookRepository.getInstance(database?.bookDao())
    }
}