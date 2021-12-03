package com.bas.google_book_app.domain.dto

import com.google.gson.annotations.SerializedName

class BookDTO(
    @field:SerializedName("id") private val mId: String?,
    @field:SerializedName(
        "volumeInfo"
    ) private val mVolumeInfo: BookVolumeInfoDTO?,
    @field:SerializedName("saleInfo") private val mSaleInfo: BookSaleInfoDTO?
) {
    fun getId(): String? {
        return mId
    }

    fun getVolumeInfo(): BookVolumeInfoDTO? {
        return mVolumeInfo
    }

    fun getSaleInfo(): BookSaleInfoDTO? {
        return mSaleInfo
    }
}