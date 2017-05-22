/*
 * Copyright (C) 2017 Juan Ramón González González (https://github.com/jrgonzalezg)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jrgonzalezg.openlibrary.books.views

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.jrgonzalezg.openlibrary.R
import com.github.jrgonzalezg.openlibrary.books.domain.BookSummary
import com.github.jrgonzalezg.openlibrary.books.views.BooksAdapter.ViewHolder
import kotlinx.android.synthetic.main.books_list_item.view.bookCoverThumbnailImageView
import kotlinx.android.synthetic.main.books_list_item.view.bookTitleTextView


class BooksAdapter(private val bookSummaries: List<BookSummary>,
    private val booksListener: BooksListener?) : Adapter<ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.books_list_item, parent, false)
    return ViewHolder(view)
  }

  fun getFirstCoverThumbnailUrl(covers: List<Int>?): String? {
    val firstCoverId: Int? = covers?.firstOrNull()

    if (firstCoverId != null) {
      return "http://covers.openlibrary.org/b/id/${firstCoverId}-M.jpg"
    } else {
      return null
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val bookSummary: BookSummary = bookSummaries[position]
    holder.bookSummary = bookSummary

    val firstCoverThumbnailUrl = getFirstCoverThumbnailUrl(bookSummary.covers)
    if (firstCoverThumbnailUrl != null) {
      Glide.with(holder.view).load(getFirstCoverThumbnailUrl(bookSummary.covers)).apply(
          RequestOptions().placeholder(R.drawable.ic_book)).into(holder.bookCoverThumbnailImageView)
    } else {
      holder.bookCoverThumbnailImageView.setImageResource(R.drawable.ic_book)
    }

    holder.bookTitleTextView.text = bookSummary.title

    holder.view.setOnClickListener { booksListener?.onBookSelected(bookSummary) }
  }

  override fun getItemCount(): Int {
    return bookSummaries.size
  }

  inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val bookCoverThumbnailImageView: ImageView
    val bookTitleTextView: TextView
    var bookSummary: BookSummary? = null

    init {
      bookCoverThumbnailImageView = view.bookCoverThumbnailImageView
      bookTitleTextView = view.bookTitleTextView
    }

    override fun toString(): String {
      return super.toString() + " '" + bookTitleTextView.text + "'"
    }
  }
}
