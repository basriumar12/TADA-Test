package com.bas.google_book_app.utilsdata

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppThreadExecutors private constructor(
    private val diskIO: Executor?,
    private val networkIO: Executor?,
    private val mainThread: Executor?
) {
    fun diskIO(): Executor? {
        return diskIO
    }

    fun mainThread(): Executor? {
        return mainThread
    }

    fun networkIO(): Executor? {
        return networkIO
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler: Handler? = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler?.post(command)
        }
    }

    companion object {
        private val LOCK: Any? = Any()
        private var sInstance: AppThreadExecutors? = null
        fun getInstance(): AppThreadExecutors? {
            if (sInstance == null) {
                if (LOCK != null) {
                    synchronized(LOCK) {
                        sInstance = AppThreadExecutors(
                            Executors.newSingleThreadExecutor(),
                            Executors.newFixedThreadPool(Constants.NUMBER_OF_THREADS_THREE),
                            MainThreadExecutor()
                        )
                    }
                }
            }
            return sInstance
        }
    }
}