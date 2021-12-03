package com.bas.google_book_app.domain.dto

import com.google.gson.annotations.SerializedName

//
class BookVolumeInfoDTO {
    @SerializedName("title")
    private var mTitle: String? = null

    @SerializedName("subtitle")
    private var mSubtitle: String? = null

    @SerializedName("authors")
    private var mAuthors: Array<String?>? = null

    @SerializedName("description")
    private var mDescription: String? = null

    @SerializedName("imageLinks")
    private var mImageLinks: BookImageLinksDTO? = null
    fun getTitle(): String? {
        return mTitle
    }

    fun setTitle(mTitle: String?) {
        this.mTitle = mTitle
    }

    fun getSubtitle(): String? {
        return mSubtitle
    }

    fun setSubtitle(mSubtitle: String?) {
        this.mSubtitle = mSubtitle
    }

    fun getAuthors(): Array<String?>? {
        return mAuthors
    }

    fun setAuthors(mAuthors: Array<String?>?) {
        this.mAuthors = mAuthors
    }

    fun getDescription(): String? {
        return mDescription
    }

    fun setDescription(description: String?) {
        mDescription = description
    }

    fun getImageLinks(): BookImageLinksDTO? {
        return mImageLinks
    }

    fun setImageLinks(imageLinks: BookImageLinksDTO?) {
        mImageLinks = imageLinks
    }
}