package com.bas.google_book_app

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class BookApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startTimber()
    }

    private fun startTimber() {
        Timber.plant(DebugTree())
    }
}