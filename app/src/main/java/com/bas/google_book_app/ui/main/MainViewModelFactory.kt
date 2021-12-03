package com.bas.google_book_app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.bas.google_book_app.repository.BookRepository

class MainViewModelFactory(
    private val mFilterBy: String?,
    private val mRepository: BookRepository?
) : NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T?>): T {
        return MainActivityViewModel(mFilterBy, mRepository) as T
    }
}