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

package com.github.jrgonzalezg.openlibrary.features.books.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.github.jrgonzalezg.openlibrary.R
import com.github.jrgonzalezg.openlibrary.app.BaseActivity
import com.github.jrgonzalezg.openlibrary.app.MyApplication
import com.github.jrgonzalezg.openlibrary.features.books.BooksActivityModule
import com.github.jrgonzalezg.openlibrary.features.books.BooksActivitySubcomponent
import com.github.jrgonzalezg.openlibrary.features.books.domain.BookSummariesError
import com.github.jrgonzalezg.openlibrary.features.books.domain.BookSummary
import com.github.jrgonzalezg.openlibrary.features.books.presenter.BooksPresenter
import com.github.jrgonzalezg.openlibrary.features.books.presenter.BooksView
import kotlinx.android.synthetic.main.book_activity.coordinatorLayout
import kotlinx.android.synthetic.main.books_activity.toolbar
import kotlinx.android.synthetic.main.books_list.booksList
import javax.inject.Inject

class BooksActivity : BaseActivity(), BooksListener, BooksView {
  @Inject
  lateinit var booksPresenter: BooksPresenter

  override fun initializeDependencyInjection() {
    val booksActivitySubcomponent: BooksActivitySubcomponent = MyApplication.myApplicationComponent.newBooksActivitySubcomponent(
        BooksActivityModule(this))

    booksActivitySubcomponent.inject(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.books_activity)

    setSupportActionBar(toolbar)
    toolbar.title = title

    booksList.setHasFixedSize(true)
  }

  override fun onResumeFragments() {
    super.onResumeFragments()

    this.booksPresenter.takeView(this)
  }

  override fun onPause() {
    super.onPause()

    this.booksPresenter.dropView(this)
  }

  override fun onBookSelected(bookSummary: BookSummary) {
    this.booksPresenter.onBookSelected(bookSummary)
  }

  override fun openBookScreen(bookKey: String) {
    val intent: Intent = BookActivity.getCallingIntent(this, bookKey)
    // TODO: Add animation
    startActivity(intent)
  }

  override fun showBookSummaries(bookSummaries: List<BookSummary>) {
    booksList.adapter = BooksAdapter(bookSummaries, this)
  }

  override fun showBookSummariesError(bookSummariesError: BookSummariesError) {
    // TODO: Add proper views for the book summaries error
    Snackbar.make(coordinatorLayout, "Should show book summaries error: $bookSummariesError}",
        Snackbar.LENGTH_LONG).show()
  }
}
