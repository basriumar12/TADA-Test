package com.bas.google_book_app;

import android.app.Application;

import timber.log.Timber;

public class BookApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startTimber();
    }

    private void startTimber() {
        Timber.plant(new Timber.DebugTree());
    }
}
