package com.bas.google_book_app.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bas.google_book_app.R
import com.bas.google_book_app.databinding.BookListItemBinding
import com.bas.google_book_app.db.BookEntry
import com.bas.google_book_app.ui.main.FavoriteBookAdapter.FavoriteViewHolder
import com.squareup.picasso.Picasso

class FavoriteBookAdapter(
    private val mContext: Context?,
    private val mOnClickHandler: FavoriteOnClickHandler?
) : RecyclerView.Adapter<FavoriteViewHolder?>() {
    private var mFavoriteBooks: MutableList<BookEntry?>? = null

    interface FavoriteOnClickHandler {
        open fun onFavItemClick(favoriteBook: BookEntry?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val favItemBinding: BookListItemBinding = DataBindingUtil
            .inflate(layoutInflater, R.layout.book_list_item, parent, false)
        return FavoriteViewHolder(favItemBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val bookEntry = mFavoriteBooks?.get(position)
        holder.bind(bookEntry)
    }

    override fun getItemCount(): Int {
        return mFavoriteBooks?.size ?: 0
    }

    fun setBooks(favoriteBooks: MutableList<BookEntry?>?) {
        mFavoriteBooks = favoriteBooks
        notifyDataSetChanged()
    }

    fun getFavoriteBooks(): MutableList<BookEntry?>? {
        return mFavoriteBooks
    }

    inner class FavoriteViewHolder(var mFavItemBinding: BookListItemBinding?) :
        RecyclerView.ViewHolder(
            mFavItemBinding?.getRoot()!!
        ), View.OnClickListener {
        fun bind(favoriteBook: BookEntry?) {
            var thumbnail = favoriteBook?.getThumbnailURL()
            thumbnail = thumbnail?.replaceFirst("^(http://)?(www\\.)?".toRegex(), "https://")
            Picasso.with(itemView.context)
                .load(thumbnail)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(mFavItemBinding?.ivThumbnail)
            mFavItemBinding?.tvTitle?.text = favoriteBook?.getTitle()
        }

        override fun onClick(v: View?) {
            val adapterPosition = adapterPosition
            val favoriteBook = mFavoriteBooks?.get(adapterPosition)
            mOnClickHandler?.onFavItemClick(favoriteBook)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}