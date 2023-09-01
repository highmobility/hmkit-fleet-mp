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

import com.highmobility.hmkitfleet.model.Brand
import com.highmobility.hmkitfleet.model.EligibilityStatus
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.put

internal class UtilityRequests(
  client: HttpClient,
  baseUrl: String,
  private val authTokenRequests: AuthTokenRequests
) : Requests(
  client,
  baseUrl
) {
  suspend fun getEligibility(
    vin: String,
    brand: Brand
  ): Response<EligibilityStatus> {
    val body = requestBody(vin, brand)
    val authToken = authTokenRequests.getAuthToken()

    if (authToken.error != null) return Response(null, authToken.error)

    val headers = mapOf(
      contentType.first to contentType.second,
      "Authorization" to "Bearer ${authToken.response?.authToken}"
    )

    val response = client.post("$baseUrl/eligibility") {
      headers.forEach { (key, value) -> header(key, value) }
      setBody(body)
    }

    return tryParseResponse(response, 200) { responseBody ->
      val eligibilityStatus = Json.decodeFromString<EligibilityStatus>(responseBody)
      if (eligibilityStatus.vin != vin) println("VIN in response does not match VIN in request")
      Response(eligibilityStatus, null)
    }
  }

  private fun requestBody(
    vin: String,
    brand: Brand,
  ): String {
    val vehicle = buildJsonObject {
      put("vin", vin)
      put("brand", Json.encodeToJsonElement(brand))
    }

    return vehicle.toString()
  }
}
