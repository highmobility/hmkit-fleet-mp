@file:OptIn(ExperimentalJsExport::class)
@file:JsExport

package com.highmobility.hmkitfleet

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging

class HMKitConfiguration private constructor(builder: Builder) :
  SharedConfiguration(builder.getKtorClient()) {

  class Builder {
    fun getKtorClient() =
      HttpClient(Js) {
        install(Logging) {
          level = LogLevel.ALL
        }
      }

    fun build() = HMKitConfiguration(this)
  }

  companion object {
    fun defaultConfiguration(): HMKitConfiguration {
      return Builder().build()
    }
  }
}