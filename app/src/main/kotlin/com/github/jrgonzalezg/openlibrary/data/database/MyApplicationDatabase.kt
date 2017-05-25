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

package com.github.jrgonzalezg.openlibrary.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.github.jrgonzalezg.openlibrary.features.books.data.database.BookSummaryDao
import com.github.jrgonzalezg.openlibrary.features.books.data.database.BookSummaryEntity

@Database(entities = arrayOf(BookSummaryEntity::class), version = 1)
@TypeConverters(Converters::class)
abstract class MyApplicationDatabase : RoomDatabase() {
  abstract fun bookSummaryDao(): BookSummaryDao

  companion object {
    private const val DB_NAME = "books.db"

    fun createInMemoryDatabase(context: Context): MyApplicationDatabase
        = Room.inMemoryDatabaseBuilder(context.applicationContext,
        MyApplicationDatabase::class.java).build()

    fun createPersistentDatabase(context: Context): MyApplicationDatabase
        = Room.databaseBuilder(context.applicationContext, MyApplicationDatabase::class.java,
        DB_NAME).build()
  }
}
