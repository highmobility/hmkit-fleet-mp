package platform

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(Js) {
  config(this)
  install(Logging) {
    level = LogLevel.ALL
  }

  engine {

  }
}

actual val currentPlatform = KotlinPlatform.JS
