package platform

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import java.util.concurrent.TimeUnit
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(OkHttp) {
  config(this)
  install(Logging) {
    level = LogLevel.ALL
  }

  engine {
    config {
      retryOnConnectionFailure(true)
      connectTimeout(0, TimeUnit.SECONDS)
    }
  }
}

actual val currentPlatform = KotlinPlatform.JVM