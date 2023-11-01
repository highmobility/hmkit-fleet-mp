package com.highmobility.hmkitfleet

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class HMKitConfiguration private constructor(builder: Builder) :
  SharedConfiguration(builder.getKtorClient()) {

  class Builder {
    private var okHttpClient: OkHttpClient? = null

    fun client(client: OkHttpClient) = apply { this.okHttpClient = client }

    fun getKtorClient() =
      HttpClient(OkHttp.create {
        preconfigured = okHttpClient ?: defaultOkHttpClient()
      }) {
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

private fun defaultOkHttpClient(): OkHttpClient {
  return OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(0, TimeUnit.SECONDS).build()
}