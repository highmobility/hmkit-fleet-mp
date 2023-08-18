package com.highmobility.hmkitfleet

import io.ktor.client.HttpClient
import platform.httpClient

//import okhttp3.OkHttpClient

class HMKitConfiguration private constructor(builder: Builder) {
    val client: HttpClient

    init {
        client = builder.client ?: httpClient()
    }
//
    class Builder {
        var client: HttpClient? = null
            private set

        fun client(client: HttpClient) = apply { this.client = client }
        fun build() = HMKitConfiguration(this)
    }

    companion object {
        fun defaultConfiguration(): HMKitConfiguration {
            return Builder().build()
        }
    }
}
