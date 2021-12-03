package com.bas.google_book_app.domain.dto

import com.bas.google_book_app.utilsdata.Constants
import com.google.gson.annotations.SerializedName

class BookImageLinksDTO {
    @SerializedName("thumbnail")
    private val mThumbnail: String? = null

    @SerializedName("small")
    private val mThumbnailSmall: String? = null

    @SerializedName("medium")
    private val mThumbnailMedium: String? = null

    @SerializedName("large")
    private val mThumbnailLarge: String? = null
    val thumbnail: String
        get() = (mThumbnailLarge
            ?: (mThumbnailMedium
                ?: (mThumbnailSmall ?: (mThumbnail ?: Constants.NO_THUMBNAIL_URL)))).toString()
}