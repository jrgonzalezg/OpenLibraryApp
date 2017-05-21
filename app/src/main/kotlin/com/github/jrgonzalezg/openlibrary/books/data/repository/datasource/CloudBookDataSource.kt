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

import com.github.jrgonzalezg.openlibrary.books.domain.BookSummary
import com.github.jrgonzalezg.openlibrary.books.data.api.OpenLibraryService
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudBookDataSource @Inject constructor(
    val openLibraryService: OpenLibraryService) : BookDataSource {
  override fun getBookSummaries(): Deferred<List<BookSummary>> {
    return async(CommonPool) {
      val response: Response<List<BookSummary>> = openLibraryService.getBookSummaries().execute()
      if (response.isSuccessful && response.body() != null) {
        response.body()!!
      } else {
        // TODO: Replace the return type with an Either or a sealed class and return a proper error
        // instead of an empty list here
        listOf()
      }
    }
  }
}
