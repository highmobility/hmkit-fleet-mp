@file:Suppress("NO_ACTUAL_FOR_EXPECT")

package platform

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient


enum class KotlinPlatform {
  JVM,JS,Native
}
expect val currentPlatform: KotlinPlatform