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

package com.github.jrgonzalezg.openlibrary.books.data.repository.datasource

import com.github.jrgonzalezg.openlibrary.books.database.BookSummaryEntity
import com.github.jrgonzalezg.openlibrary.books.domain.BookSummariesError
import com.github.jrgonzalezg.openlibrary.books.domain.BookSummary
import com.github.jrgonzalezg.openlibrary.database.MyApplicationDatabase
import com.github.jrgonzalezg.openlibrary.domain.Result
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.funktionale.either.Disjunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalBookDataSource @Inject constructor(
    private val myApplicationDatabase: MyApplicationDatabase) : BookDataSource {
  override fun getBookSummaries(): Result<BookSummariesError, List<BookSummary>> {
    return async(CommonPool) {
      val bookSummaries = myApplicationDatabase.bookSummaryDao().getAll()
      if (bookSummaries.isEmpty()) {
        Disjunction.left(BookSummariesError.BookSummariesNotFound)
      } else {
        Disjunction.right(bookSummaries)
      }
    }
  }

  fun insertBookSummaries(bookSummaries: List<BookSummary>) {
    val bookSummaryEntities = bookSummaries.map { BookSummaryEntity(it.key, it.title, it.covers) }
    myApplicationDatabase.bookSummaryDao().insertOrUpdateAll(bookSummaryEntities)
  }
}
