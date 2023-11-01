/*
 * The MIT License
 *
 * Copyright (c) 2023- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.hmkitfleet.network

import com.highmobility.hmkitfleet.ServiceAccountApiConfiguration
import com.highmobility.hmkitfleet.model.AuthToken
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.http.HttpStatusCode
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.koin.core.logger.Logger
import platform.Crypto
import kotlin.time.Duration.Companion.minutes

internal class AuthTokenRequests(
  client: HttpClient,
  private val crypto: Crypto,
  baseUrl: String,
  private val configuration: ServiceAccountApiConfiguration,
  private val cache: Cache,
) : Requests(
  client,
  baseUrl,
) {
  private suspend fun sendAuthTokenRequest(): HttpResponse {
    val body = buildJsonObject {
      put("assertion", getJwt(configuration, crypto))
    }

    val headers = mapOf(
      contentType.first to contentType.second
    )

    val response = client.post("$baseUrl/auth_tokens") {
      headers.forEach { (key, value) -> header(key, value) }
      setBody(body.toString())
    }

    return response
  }

  suspend fun getAuthToken(): Response<AuthToken> {
    val cachedToken = cache.authToken
    if (cachedToken != null) return Response(cachedToken)

    val response = sendAuthTokenRequest()

    return try {
      if (response.status == HttpStatusCode.Created) {
        val authToken = Json.decodeFromString<AuthToken>(response.bodyAsText())
        cache.authToken = authToken
        Response(cache.authToken)
      } else {
        parseError(response.bodyAsText())
      }
    } catch (e: Exception) {
      val detail = e.message.toString()
      Response(null, genericError(detail))
    }
  }
}
