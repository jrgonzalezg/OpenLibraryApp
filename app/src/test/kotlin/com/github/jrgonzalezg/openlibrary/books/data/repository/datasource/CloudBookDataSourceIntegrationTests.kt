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

import com.github.jrgonzalezg.openlibrary.app.NetworkIntegrationTests
import com.github.jrgonzalezg.openlibrary.books.domain.BookSummariesError
import com.github.jrgonzalezg.openlibrary.books.domain.BookSummary
import io.kotlintest.KTestJUnitRunner
import io.kotlintest.matchers.beEmpty
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNot
import kotlinx.coroutines.experimental.runBlocking
import org.funktionale.either.Disjunction
import org.junit.runner.RunWith

@RunWith(KTestJUnitRunner::class)
class CloudBookDataSourceIntegrationTests : NetworkIntegrationTests() {
  init {
    "openLibraryService.getBookSummaries() should return a non empty list of book summaries" {
      val cloudBookDataSource = networkComponent.cloudBookDataSource()

      runBlocking {
        val result: Disjunction<BookSummariesError, List<BookSummary>> = cloudBookDataSource.getBookSummaries().await()

        result.isRight() shouldBe true
        result.get() shouldNot beEmpty()
      }
    }
  }
}
