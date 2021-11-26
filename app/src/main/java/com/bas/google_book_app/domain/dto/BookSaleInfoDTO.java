package com.bas.google_book_app.domain.dto;

import com.google.gson.annotations.SerializedName;

public class BookSaleInfoDTO {

    @SerializedName("buyLink")
    private String mBuyLink;

    public BookSaleInfoDTO(String buyLink) {
        this.mBuyLink = buyLink;
    }

    public String getBuyLink() {
        return mBuyLink;
    }
}
