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
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.koin.core.logger.Logger
import kotlin.time.Duration.Companion.minutes

internal class AuthTokenRequests(
    client: HttpClient,
    private val crypto: Any,
    baseUrl: String,
    private val configuration: ServiceAccountApiConfiguration,
    private val cache: Cache,
) : Requests(
    client,
    baseUrl,
) {
//    private fun getUrlEncodedJsonRequest(): Request {
//        val body = buildJsonObject {
//            put("assertion", getJwt(configuration, crypto))
//        }
//
//        val newBody = Json.encodeToString(body).toRequestBody("application/json".toMediaType())
//
//        val request = Request.Builder()
//            .url("$baseUrl/auth_tokens")
//            .header("Content-Type", "application/json")
//            .post(newBody)
//            .build()
//
//        return request
//    }

    suspend fun getAuthToken(): Response<AuthToken> {
        return Response(AuthToken("token", Clock.System.now().minus(5.minutes), Clock.System.now().plus(5.minutes)))
//        val cachedToken = cache.authToken
//        if (cachedToken != null) return Response(cachedToken)
//
//        val request = getUrlEncodedJsonRequest()
//
//        printRequest(request)
//        val call = client.newCall(request)
//
//        val response = call.await()
//        val responseBody = printResponse(response)
//
//        return try {
//            if (response.code == HttpURLConnection.HTTP_CREATED) {
//                cache.authToken = Json.decodeFromString(responseBody)
//                Response(cache.authToken)
//            } else {
//                parseError(responseBody)
//            }
//        } catch (e: java.lang.Exception) {
//            val detail = e.message.toString()
//            Response(null, genericError(detail))
//        }
    }
}
