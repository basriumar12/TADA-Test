package com.bas.google_book_app.domain

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import java.util.*

class Book() : Parcelable {
     var mId: String? = null
     var mTitle: String? = null
     var mSubtitle: String? = null
     var mAuthors: Array<String?>? = null
     var mDescription: String? = null
    var mBuyLink: String? = null
    var mThumbnailURL: String? = null

    constructor(parcel: Parcel) : this() {
        mId = parcel.readString()
        mTitle = parcel.readString()
        mSubtitle = parcel.readString()
        mAuthors = parcel.createStringArray()
        mDescription = parcel.readString()
        mBuyLink = parcel.readString()
        mThumbnailURL = parcel.readString()
    }

    constructor(
        mId: String?,
        mTitle: String?,
        mSubtitle: String?,
        mAuthors: Array<String?>?,
        mDescription: String?,
        buyLink: String?,
        mThumbnailURL: String?
    ) : this() {
        this.mId = mId
        this.mTitle = mTitle
        this.mSubtitle = mSubtitle
        this.mAuthors = mAuthors
        this.mDescription = mDescription
        mBuyLink = buyLink
        this.mThumbnailURL = mThumbnailURL
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mId)
        parcel.writeString(mTitle)
        parcel.writeString(mSubtitle)
        parcel.writeStringArray(mAuthors)
        parcel.writeString(mDescription)
        parcel.writeString(mBuyLink)
        parcel.writeString(mThumbnailURL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }


}