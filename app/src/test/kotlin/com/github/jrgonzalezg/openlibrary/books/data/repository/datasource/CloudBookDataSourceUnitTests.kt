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

import com.github.jrgonzalezg.openlibrary.app.NetworkUnitTests
import com.github.jrgonzalezg.openlibrary.books.data.api.Endpoints
import com.github.jrgonzalezg.openlibrary.books.domain.BookSummary
import io.kotlintest.KTestJUnitRunner
import io.kotlintest.matchers.shouldBe
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.runner.RunWith

@RunWith(KTestJUnitRunner::class)
class CloudBookDataSourceUnitTests : NetworkUnitTests() {
  init {
    "openLibraryService.getBookSummaries() should return the expected book summaries" {
      val cloudBookDataSource = networkComponent.cloudBookDataSource()

      val bookSummariesJson = this.javaClass.getResource(
          "/openlibrary/book_summaries.json").readText()
      mockWebServer.enqueue(MockResponse().setBody(bookSummariesJson))

      val book1 = BookSummary("/books/OL26289608M", "Christine", listOf(7960223))
      val book2 = BookSummary("/books/OL24195641M", "The Eyes of the Dragon", listOf(6374558))
      val expectedBookSummaries = listOf(book1, book2)

      runBlocking {
        cloudBookDataSource.getBookSummaries().await() shouldBe expectedBookSummaries
      }

      val recordedRequest: RecordedRequest = mockWebServer.takeRequest()
      recordedRequest.path shouldBe Endpoints.BOOK_SUMMARIES_QUERY
    }
  }
}
