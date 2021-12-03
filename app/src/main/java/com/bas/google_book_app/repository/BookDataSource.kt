package com.bas.google_book_app.repository

import androidx.paging.PageKeyedDataSource
import com.bas.google_book_app.domain.Book
import com.bas.google_book_app.domain.dto.BookResponseDTO
import com.bas.google_book_app.network_service.GoogleBookAPI
import com.bas.google_book_app.network_service.RetrofitServiceController
import com.bas.google_book_app.utilsdata.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class BookDataSource : PageKeyedDataSource<Int?, Book?>() {
    private val mGoogleBookApi: GoogleBookAPI?
    override fun loadInitial(
        params: LoadInitialParams<Int?>,
        callback: LoadInitialCallback<Int?, Book?>
    ) {
        mGoogleBookApi?.getBooks(
            Constants.API_KEY,
            Constants.QUERY,
            Constants.PAGE,
            Constants.MAX_RESULTS,
            Constants.PROJECTION
        )
            ?.enqueue(object : Callback<BookResponseDTO?> {
                override fun onResponse(
                    call: Call<BookResponseDTO?>?,
                    response: Response<BookResponseDTO?>?
                ) {
                    if (response?.isSuccessful() == true) {
                        response?.body()?.getBookResults()?.let {
                            callback.onResult(
                                it,
                                Constants.PREVIOUS_PAGE_KEY_ONE, Constants.NEXT_PAGE_KEY_TWO
                            )
                        }
                    } else if (response?.code() == Constants.RESPONSE_CODE_API_STATUS) {
                        Timber.e(
                            "The Api key is invalid. Response code: %s",
                            response?.code()
                        )
                    } else {
                        Timber.e("Response Code: %s", response?.code())
                    }
                }

                override fun onFailure(call: Call<BookResponseDTO?>?, t: Throwable?) {
                    Timber.e(
                        "Error caused failure initializing a PageList: %s",
                        t?.message
                    )
                }
            })
    }

    override fun loadBefore(
        params: LoadParams<Int?>,
        callback: LoadCallback<Int?, Book?>
    ) {
    }

    override fun loadAfter(
        params: LoadParams<Int?>,
        callback: LoadCallback<Int?, Book?>
    ) {
        val currentPage = params.key
        mGoogleBookApi?.getBooks(
            Constants.API_KEY,
            Constants.QUERY,
            currentPage,
            Constants.MAX_RESULTS,
            Constants.PROJECTION
        )
            ?.enqueue(object : Callback<BookResponseDTO?> {
                override fun onResponse(
                    call: Call<BookResponseDTO?>?,
                    response: Response<BookResponseDTO?>?
                ) {
                    if (response?.isSuccessful() == true) {
                        val nextKey = currentPage + Constants.NEXT_PAGE_KEY_TWO
                        if (nextKey < response?.body()?.getTotalItems()!!) {
                            response?.body()?.getBookResults()
                                ?.let { callback.onResult(it, nextKey) }
                        }
                    }
                }

                override fun onFailure(call: Call<BookResponseDTO?>?, t: Throwable?) {
                    Timber.e("Failure appending the page: %s", t?.message)
                }
            })
    }

    init {
        mGoogleBookApi = RetrofitServiceController.getClient()?.create(GoogleBookAPI::class.java)
    }
}