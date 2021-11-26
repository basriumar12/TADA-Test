package com.bas.google_book_app.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bas.google_book_app.db.BookEntry;
import com.bas.google_book_app.repository.BookRepository;
import com.bas.google_book_app.utilsdata.AppThreadExecutors;

public class DetailViewModel extends ViewModel {
    private final BookRepository mRepository;
    private LiveData<BookEntry> mBookEntry;


    public DetailViewModel(BookRepository repository, String bookId) {
        mRepository = repository;
        mBookEntry = mRepository.getFavoriteBookById(bookId);
    }

    public LiveData<BookEntry> getBookEntry() {
        return mBookEntry;
    }

    public void addFavoriteBook(BookEntry book) {
        AppThreadExecutors.getInstance().diskIO().execute(() -> mRepository.addFavoriteBook(book));
    }

    public void removeFavoriteBook(BookEntry book) {
        AppThreadExecutors.getInstance().diskIO().execute(() -> mRepository.removeFavoriteBook(book));
    }
}
