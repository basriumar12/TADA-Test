package com.bas.google_book_app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.bas.google_book_app.repository.BookRepository

class DetailViewModelFactory(
    private val mRepository: BookRepository?,
    private val mBookId: String?
) : NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(mRepository, mBookId) as T
    }
}