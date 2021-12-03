package com.bas.google_book_app.domain.dto

import com.bas.google_book_app.domain.Book
import com.google.gson.annotations.SerializedName
import java.util.*

class BookResponseDTO(@field:SerializedName("totalItems") private val mTotalItems: Int) {
    @SerializedName("items")
    private val mBookResults: MutableList<BookDTO?>? = null
    fun getTotalItems(): Int {
        return mTotalItems
    }

    fun getBookResults(): MutableList<Book?>? {
        return convertBookDTO()
    }

    private fun convertBookDTO(): MutableList<Book?>? {
        val books: MutableList<Book?> = ArrayList()
        if (mBookResults != null) {
            for (dto in mBookResults) {
                val volumeInfo = dto?.getVolumeInfo()
                val saleInfo = dto?.getSaleInfo()
                var thumbnail = ""
                if (volumeInfo?.getImageLinks() != null) {
                    thumbnail = volumeInfo?.getImageLinks()?.thumbnail.toString()
                }
                val book = Book(
                    dto?.getId(), volumeInfo?.getTitle(), volumeInfo?.getSubtitle(),
                    volumeInfo?.getAuthors(), volumeInfo?.getDescription(), saleInfo?.getBuyLink(),
                    thumbnail
                )
                books.add(book)
            }
        }
        return books
    }
}