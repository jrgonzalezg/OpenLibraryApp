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

import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Module
class TestNetworkModule : NetworkModule() {
  val mockWebServer: MockWebServer = MockWebServer()
  override val HTTP_LOGGING_ENABLED: Boolean = false
  override val OPEN_LIBRARY_BASE_URL = mockWebServer.url("").toString()

  @Provides
  @Singleton
  fun provideMockWebServer(): MockWebServer = mockWebServer
}
