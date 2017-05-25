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

package com.github.jrgonzalezg.openlibrary.features.books.presenter

import com.github.jrgonzalezg.openlibrary.features.books.domain.BookSummariesError
import com.github.jrgonzalezg.openlibrary.features.books.domain.BookSummary
import com.github.jrgonzalezg.openlibrary.features.books.usecase.GetBookSummariesUseCase
import com.github.jrgonzalezg.openlibrary.presenter.BasePresenter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.funktionale.either.Disjunction
import javax.inject.Inject

class BooksPresenter @Inject constructor(
    private val getBookSummariesUseCase: GetBookSummariesUseCase) : BasePresenter<BooksView>() {
  private fun loadBookSummaries() {
    launch(job!! + UI) {
      val bookSummaries: Disjunction<BookSummariesError, List<BookSummary>> = getBookSummariesUseCase.getBookSummaries().await()
      view?.showBookSummaries(bookSummaries)
    }
  }

  override fun takeView(view: BooksView) {
    super.takeView(view)

    loadBookSummaries()
  }

  fun onBookSelected(bookSummary: BookSummary) {
    view?.openBookScreen(bookSummary)
  }
}

interface BooksView {
  fun openBookScreen(bookSummary: BookSummary): Unit
  fun showBookSummaries(
      bookSummaries: Disjunction<BookSummariesError, List<BookSummary>>): Unit
}
