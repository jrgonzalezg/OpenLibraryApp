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

import com.github.jrgonzalezg.openlibrary.BuildConfig
import com.github.jrgonzalezg.openlibrary.features.books.data.api.OpenLibraryService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
  val HTTP_LOGGING_ENABLED: Boolean = BuildConfig.HTTP_LOGGING_ENABLED
  val OPEN_LIBRARY_BASE_URL: String = "https://openlibrary.org"

  @Provides
  @Singleton
  protected fun provideOkHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()

    if (HTTP_LOGGING_ENABLED) {
      val httpLoggingInterceptor = HttpLoggingInterceptor()
      httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
      okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }

    return okHttpClientBuilder.build()
  }

  @Provides
  @Singleton
  protected fun provideOpenLibraryService(retrofit: Retrofit): OpenLibraryService {
    return retrofit.create(OpenLibraryService::class.java)
  }

  @Provides
  @Singleton
  protected fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl(OPEN_LIBRARY_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit
  }
}
