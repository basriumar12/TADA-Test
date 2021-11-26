package com.bas.google_book_app.network_service;

import com.bas.google_book_app.domain.dto.BookResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBookAPI {

    @GET("volumes/")
    Call<BookResponseDTO> getBooks(
            @Query("key") String apiKey,
            @Query("q") String query,
            @Query("startIndex") int page,
            @Query("maxResults") int maxResults,
            @Query("projection") String projection
    );
}
