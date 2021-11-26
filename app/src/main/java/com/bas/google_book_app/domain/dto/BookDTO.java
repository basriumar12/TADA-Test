package com.bas.google_book_app.domain.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class BookDTO {
    @SerializedName("id")
    private String mId;

    @SerializedName("volumeInfo")
    private BookVolumeInfoDTO mVolumeInfo;

    @SerializedName("saleInfo")
    @Nullable
    private BookSaleInfoDTO mSaleInfo;

    public BookDTO(String bookId, BookVolumeInfoDTO volumeInfo, @Nullable BookSaleInfoDTO saleInfo) {
        mId = bookId;
        mVolumeInfo = volumeInfo;
        mSaleInfo = saleInfo;
    }

    public String getId() {
        return mId;
    }

    public BookVolumeInfoDTO getVolumeInfo() {
        return mVolumeInfo;
    }

    @Nullable
    public BookSaleInfoDTO getSaleInfo() {
        return mSaleInfo;
    }
}
