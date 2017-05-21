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

import android.os.Bundle
import com.github.jrgonzalezg.openlibrary.R
import com.github.jrgonzalezg.openlibrary.app.BaseActivity
import com.github.jrgonzalezg.openlibrary.app.MyApplication
import com.github.jrgonzalezg.openlibrary.books.domain.BookSummary
import com.github.jrgonzalezg.openlibrary.books.BooksActivityModule
import com.github.jrgonzalezg.openlibrary.books.BooksActivitySubcomponent
import com.github.jrgonzalezg.openlibrary.books.presenter.BooksPresenter
import com.github.jrgonzalezg.openlibrary.books.presenter.BooksView
import javax.inject.Inject

class BooksActivity : BaseActivity(), BooksView {
  @Inject
  lateinit var booksPresenter: BooksPresenter

  override fun initializeDependencyInjection() {
    val booksActivitySubcomponent: BooksActivitySubcomponent = MyApplication.myApplicationComponent.newBooksActivitySubcomponent(
        BooksActivityModule(this))

    booksActivitySubcomponent.inject(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_books)
  }

  override fun onResumeFragments() {
    super.onResumeFragments()

    this.booksPresenter.takeView(this)
  }

  override fun onPause() {
    super.onPause()

    this.booksPresenter.dropView(this)
  }

  override fun showBookSummaries(bookSummaries: List<BookSummary>) {

  }
}
