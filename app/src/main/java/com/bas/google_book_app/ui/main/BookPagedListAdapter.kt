package com.bas.google_book_app.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bas.google_book_app.R
import com.bas.google_book_app.databinding.BookListItemBinding
import com.bas.google_book_app.domain.Book
import com.bas.google_book_app.ui.main.BookPagedListAdapter.BookPagedViewHolder
import com.squareup.picasso.Picasso
import java.util.*

class BookPagedListAdapter(private val mOnClickHandler: BookPagedListAdapterOnClickHandler?) :
    PagedListAdapter<Book?, BookPagedViewHolder?>(
        DIFF_CALLBACK!!
    ) {
    interface BookPagedListAdapterOnClickHandler {
        open fun onItemClick(book: Book?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookPagedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val mBookItemBinding: BookListItemBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.book_list_item, parent, false
        )
        return BookPagedViewHolder(mBookItemBinding)
    }

    override fun onBindViewHolder(holder: BookPagedViewHolder, position: Int) {
        holder.bind(Objects.requireNonNull(getItem(position)))
    }

    inner class BookPagedViewHolder(private val mBookItemBinding: BookListItemBinding?) :
        RecyclerView.ViewHolder(
            mBookItemBinding?.getRoot()!!
        ), View.OnClickListener {
        fun bind(book: Book?) {
            var thumbnail = book?.mThumbnailURL
            thumbnail = thumbnail?.replaceFirst("^(http://)?(www\\.)?".toRegex(), "https://")
            Picasso.with(itemView.context)
                .load(thumbnail)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(mBookItemBinding?.ivThumbnail)
            mBookItemBinding?.tvTitle?.text = book?.mTitle.toString()
        }

        override fun onClick(v: View?) {
            val adapterPosition = adapterPosition
            val book = getItem(adapterPosition)
            mOnClickHandler?.onItemClick(book)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Book?>? =
            object : DiffUtil.ItemCallback<Book?>() {
                override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                    return oldItem?.mId === newItem?.mId
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                    return oldItem == newItem
                }


            }
    }
}