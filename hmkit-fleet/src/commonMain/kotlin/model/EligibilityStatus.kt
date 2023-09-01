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
package com.highmobility.hmkitfleet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class EligibilityStatus(
  val vin: String,
  val eligible: Boolean,
  @SerialName("data_delivery")
  val dataDelivery: List<DataDelivery> = emptyList(),
  @SerialName("connectivity_status")
  val connectivityStatus: ConnectivityStatus? = null,
  @SerialName("primary_user_assigned")
  val primaryUserAssigned: Boolean? = null
) {
  @Serializable
  enum class DataDelivery {
    @SerialName("pull")
    PULL,

    @SerialName("push")
    PUSH
  }

  @Serializable
  enum class ConnectivityStatus {
    @SerialName("activated")
    ACTIVATED,

    @SerialName("deactivated")
    DEACTIVATED,

    @SerialName("unknown")
    UNKNOWN
  }
}
