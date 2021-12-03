package com.bas.google_book_app.network_service

import com.bas.google_book_app.utilsdata.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServiceController {
    private var sRetrofit: Retrofit? = null
    fun getClient(): Retrofit? {
        if (sRetrofit == null) {
            sRetrofit = Retrofit.Builder()
                .baseUrl(Constants.BOOK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return sRetrofit
    }
}