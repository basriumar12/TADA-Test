package com.bas.google_book_app.network_service

import com.bas.google_book_app.domain.dto.BookResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBookAPI {
    @GET("volumes/")
    open fun getBooks(
        @Query("key") apiKey: String?,
        @Query("q") query: String?,
        @Query("startIndex") page: Int,
        @Query("maxResults") maxResults: Int,
        @Query("projection") projection: String?
    ): Call<BookResponseDTO?>?
}