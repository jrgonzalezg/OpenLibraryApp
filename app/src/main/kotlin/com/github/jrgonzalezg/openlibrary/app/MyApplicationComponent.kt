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

package com.github.jrgonzalezg.openlibrary.app

import com.github.jrgonzalezg.openlibrary.data.database.MyApplicationDatabase
import com.github.jrgonzalezg.openlibrary.features.books.BookActivityModule
import com.github.jrgonzalezg.openlibrary.features.books.BookActivitySubcomponent
import com.github.jrgonzalezg.openlibrary.features.books.BooksActivityModule
import com.github.jrgonzalezg.openlibrary.features.books.BooksActivitySubcomponent
import com.github.jrgonzalezg.openlibrary.features.books.data.api.OpenLibraryService
import com.github.jrgonzalezg.openlibrary.features.books.data.repository.BookRepository
import com.github.jrgonzalezg.openlibrary.features.books.data.repository.datasource.CloudBookDataSource
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(MyApplicationModule::class, NetworkModule::class))
@Singleton
interface MyApplicationComponent {
  fun inject(myApplication: MyApplication)

  fun bookRepository(): BookRepository
  fun cloudBookDataSource(): CloudBookDataSource
  fun myApplication(): MyApplication
  fun myApplicationDatabase(): MyApplicationDatabase
  fun openLibraryService(): OpenLibraryService

  fun newBookActivitySubcomponent(
      bookActivityModule: BookActivityModule): BookActivitySubcomponent

  fun newBooksActivitySubcomponent(
      booksActivityModule: BooksActivityModule): BooksActivitySubcomponent
}
