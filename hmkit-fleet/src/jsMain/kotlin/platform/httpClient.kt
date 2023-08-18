package platform

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.js.Js

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(Js) {
    config(this)

    engine {

    }
}
