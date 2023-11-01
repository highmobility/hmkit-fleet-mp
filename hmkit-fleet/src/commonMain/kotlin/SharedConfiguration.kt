package com.highmobility.hmkitfleet

import io.ktor.client.HttpClient

open class SharedConfiguration(
  val client: HttpClient
)