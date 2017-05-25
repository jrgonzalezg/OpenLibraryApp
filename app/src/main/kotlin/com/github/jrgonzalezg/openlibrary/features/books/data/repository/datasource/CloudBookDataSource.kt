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

package com.github.jrgonzalezg.openlibrary.features.books.data.repository.datasource

import com.github.jrgonzalezg.openlibrary.domain.Result
import com.github.jrgonzalezg.openlibrary.features.books.data.api.OpenLibraryService
import com.github.jrgonzalezg.openlibrary.features.books.domain.Book
import com.github.jrgonzalezg.openlibrary.features.books.domain.BookError
import com.github.jrgonzalezg.openlibrary.features.books.domain.BookSummariesError
import com.github.jrgonzalezg.openlibrary.features.books.domain.BookSummary
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.funktionale.either.Disjunction
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudBookDataSource @Inject constructor(
    private val openLibraryService: OpenLibraryService) : BookDataSource {
  override fun getBook(key: String): Result<BookError, Book> {
    return async(CommonPool) {
      val response: Response<Book> = openLibraryService.getBook(key).execute()
      if (response.isSuccessful) {
        val maybeBook: Book? = response.body()
        if (maybeBook == null) {
          Disjunction.left(BookError.BookNotFound)
        } else {
          Disjunction.right(maybeBook)
        }
      } else {
        Disjunction.left(BookError.BookUnavailable)
      }
    }
  }

  override fun getBookSummaries(): Result<BookSummariesError, List<BookSummary>> {
    return async(CommonPool) {
      val response: Response<List<BookSummary>> = openLibraryService.getBookSummaries().execute()
      if (response.isSuccessful) {
        val maybeBookSummaries: List<BookSummary>? = response.body()
        if (maybeBookSummaries == null || maybeBookSummaries.isEmpty()) {
          Disjunction.left(BookSummariesError.BookSummariesNotFound)
        } else {
          Disjunction.right(maybeBookSummaries)
        }
      } else {
        Disjunction.left(BookSummariesError.BookSummariesUnavailable)
      }
    }
  }
}
