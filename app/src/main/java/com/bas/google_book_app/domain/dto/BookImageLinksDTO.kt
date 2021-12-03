package com.bas.google_book_app.domain.dto;

import com.google.gson.annotations.SerializedName;

import static com.bas.google_book_app.utilsdata.Constants.NO_THUMBNAIL_URL;

public class BookImageLinksDTO {

    @SerializedName("thumbnail")
    private String mThumbnail;

    @SerializedName("small")
    private String mThumbnailSmall;

    @SerializedName("medium")
    private String mThumbnailMedium;

    @SerializedName("large")
    private String mThumbnailLarge;

    public String getThumbnail() {
        if (mThumbnailLarge != null) return mThumbnailLarge;
        if (mThumbnailMedium != null) return mThumbnailMedium;
        if (mThumbnailSmall != null) return mThumbnailSmall;
        if (mThumbnail != null) return mThumbnail;
        return NO_THUMBNAIL_URL;
    }
}
