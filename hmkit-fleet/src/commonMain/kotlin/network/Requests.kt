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
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequest
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import platform.Crypto
import platform.KotlinPlatform
import platform.Logger
import platform.currentPlatform
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

internal open class Requests(
  val client: HttpClient,
  val baseUrl: String
) {
  val contentType = Pair("Content-Type", "application/json")

  val json by lazy { Json { prettyPrint = true } }

  suspend inline fun <T> tryParseResponse(
    response: HttpResponse,
    expectedResponseCode: Int,
    block: (body: String) -> (Response<T>)
  ): Response<T> {
    val responseBody = response.bodyAsText()

    return try {
      if (response.status.value == expectedResponseCode) {
        block(responseBody)
      } else {
        parseError(responseBody)
      }
    } catch (e: Exception) {
      val detail = e.message.toString()
      Response(null, genericError(detail))
    }
  }

  fun <T> parseError(responseBody: String): Response<T> {
    val json = Json.parseToJsonElement(responseBody)
    if (json is JsonObject) {
      // there are 3 error formats
      val errors = json["errors"] as? JsonArray

      return if (errors != null && errors.size > 0) {
        val error =
          Json.decodeFromJsonElement<Error>(errors.first())
        Response(null, error)
      } else {
        val error = Error(
          json["error"]?.jsonPrimitive?.content ?: "Unknown server response",
          json["error_description"]?.jsonPrimitive?.content
        )

        Response(null, error)
      }
    } else if (json is JsonArray) {
      if (json.size > 0) {
        val error = Json.decodeFromJsonElement<Error>(json.first())
        return Response(null, error)
      }
    }

    return Response(null, genericError("Unknown server response"))
  }

  @OptIn(ExperimentalEncodingApi::class)
  internal fun getJwt(configuration: ServiceAccountApiConfiguration, crypto: Crypto): String {
    val header = buildJsonObject {
      put("alg", "ES256")
      put("typ", "JWT")
    }.toString()

    val body = buildJsonObject {
      put("ver", configuration.version)
      put("iss", configuration.serviceAccountPrivateKeyId)
      put("aud", baseUrl)
      put("jti", configuration.createJti())
      put("iat", configuration.createIat())
    }.toString()

    return if (currentPlatform == KotlinPlatform.JVM) {
      val base64 = Base64.UrlSafe

      val headerBase64 = base64.encode(header.toByteArray())
      val bodyBase64 = base64.encode(body.toByteArray())

      val jwtContent = "$headerBase64.$bodyBase64"
      val privateKey = configuration.serviceAccountPrivateKey

      val jwt = crypto.signJWTJvm(jwtContent, privateKey)
      "$jwtContent.${base64.encode(jwt)}"
    } else {
      val jwt = crypto.signJWTJs(header, body, configuration.serviceAccountPrivateKey)
      jwt
    }
  }

  internal fun genericError(detail: String? = null): Error {
    val genericError = Error("Unknown server response", detail)
    return genericError
  }
}
