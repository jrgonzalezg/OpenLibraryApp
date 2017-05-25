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

import com.github.jrgonzalezg.openlibrary.features.books.domain.Book
import com.github.jrgonzalezg.openlibrary.features.books.domain.BookError
import com.github.jrgonzalezg.openlibrary.features.books.usecase.GetBookUseCase
import com.github.jrgonzalezg.openlibrary.presenter.BasePresenter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.funktionale.either.Disjunction
import javax.inject.Inject

class BookPresenter @Inject constructor(
    private val getBookUseCase: GetBookUseCase) : BasePresenter<BookView>() {
  var bookKey: String? = null

  private fun loadBook() {
    launch(job!! + UI) {
      var book: Disjunction<BookError, Book> = Disjunction.left(BookError.BookNotFound)

      // TODO: Improve handling of bookKey and the possibility of it being null
      bookKey?.let {
        book = getBookUseCase.getBook(it).await()
      }

      view?.showBook(book)
    }
  }

  override fun takeView(view: BookView) {
    super.takeView(view)

    loadBook()
  }
}

interface BookView {
  fun showBook(book: Disjunction<BookError, Book>): Unit
}
