package com.bas.google_book_app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "book")
class BookEntry(
    @field:ColumnInfo(name = "book_id") private var bookId: String?,
    @field:ColumnInfo(name = "book_title") private var mTitle: String?,
    @field:ColumnInfo(
        name = "book_subtitle"
    ) private var mSubtitle: String?,
    @field:ColumnInfo(name = "book_authors") private var mAuthors: String?,
    @field:ColumnInfo(name = "book_description") private var mDescription: String?,
    @field:ColumnInfo(
        name = "book_buy_link"
    ) private var mBuyLink: String?,
    @field:ColumnInfo(name = "book_thumbnail_url") private var mThumbnailURL: String?
) {
    @PrimaryKey(autoGenerate = true)
    private var id = 0
    fun getId(): Int {
        return id
    }

    fun getBookId(): String? {
        return bookId
    }

    fun getTitle(): String? {
        return mTitle
    }

    fun getSubtitle(): String? {
        return mSubtitle
    }

    fun getAuthors(): String? {
        return mAuthors
    }

    fun getDescription(): String? {
        return mDescription
    }

    fun getBuyLink(): String? {
        return mBuyLink
    }

    fun getThumbnailURL(): String? {
        return mThumbnailURL
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun setBookId(bookId: String?) {
        this.bookId = bookId
    }

    fun setTitle(mTitle: String?) {
        this.mTitle = mTitle
    }

    fun setSubtitle(mSubtitle: String?) {
        this.mSubtitle = mSubtitle
    }

    fun setAuthors(mAuthors: String?) {
        this.mAuthors = mAuthors
    }

    fun setDescription(mDescription: String?) {
        this.mDescription = mDescription
    }

    fun setBuyLink(mBuyLink: String?) {
        this.mBuyLink = mBuyLink
    }

    fun setThumbnailURL(mThumbnailURL: String?) {
        this.mThumbnailURL = mThumbnailURL
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val bookEntry = o as BookEntry?
        return bookId == bookEntry?.bookId &&
                mTitle == bookEntry?.mTitle &&
                mSubtitle == bookEntry?.mSubtitle &&
                mAuthors == bookEntry?.mAuthors &&
                mDescription == bookEntry?.mDescription &&
                mBuyLink == bookEntry?.mBuyLink &&
                mThumbnailURL == bookEntry?.mThumbnailURL
    }

    override fun hashCode(): Int {
        return Objects.hash(
            bookId,
            mTitle,
            mSubtitle,
            mAuthors,
            mDescription,
            mBuyLink,
            mThumbnailURL
        )
    }
}