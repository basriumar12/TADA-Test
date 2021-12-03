package com.bas.google_book_app.utilsdata

import com.bas.google_book_app.BuildConfig

object Constants {
    /**
     * Base URL from Google Book API
     */
    val BOOK_BASE_URL: String? = "https://www.googleapis.com/books/v1/"

    /**
     * Constants for network call
     */
    val QUERY: String? = "android"
    val PROJECTION: String? = "lite"
    const val PAGE = 0
    const val MAX_RESULTS = 20
    val API_KEY: String? = BuildConfig.API_KEY

    /**
     * Number of threads for the pool
     */
    const val NUMBER_OF_THREADS_THREE = 3

    /**
     * API Status code for invalid API key or Authentication failed
     */
    const val RESPONSE_CODE_API_STATUS = 401

    /**
     * Room Database name
     */
    val DATABASE_NAME: String? = "books"

    /**
     * Data Source Page control
     */
    const val PREVIOUS_PAGE_KEY_ONE = 0
    const val NEXT_PAGE_KEY_TWO = 21
    const val REQUEST_CODE_DIALOG = 0
    val LAYOUT_MANAGER_STATE: String? = "layout_manager_state"

    /**
     * Paged List configuration
     */
    const val INITIAL_LOAD_SIZE_HINT = 10
    const val PAGE_SIZE = 20
    const val PREFETCH_DISTANCE = 50

    /**
     * Image View Ratio
     */
    const val TWO = 2
    const val THREE = 3

    /**
     * Book Grid configuration
     */
    const val GRID_SPAN_COUNT = 2
    const val GRID_SPACING = 8
    const val GRID_INCLUDE_EDGE = true
    val NO_THUMBNAIL_URL: String? = ""
    val EXTRA_BOOK: String? = "extra_book"
}