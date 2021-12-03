package com.bas.google_book_app.domain.dto

import com.google.gson.annotations.SerializedName

class BookSaleInfoDTO(@field:SerializedName("buyLink") private val mBuyLink: String?) {
    fun getBuyLink(): String? {
        return mBuyLink
    }
}