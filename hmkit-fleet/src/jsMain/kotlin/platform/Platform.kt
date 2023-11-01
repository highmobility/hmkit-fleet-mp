package platform

actual val currentPlatform = KotlinPlatform.JS

actual fun uuid(): String {
  return uuid.v4()
}

@JsModule("uuid")
@JsNonModule
external object uuid {
  fun v4(): String
}

// Logging

actual fun createLogger(): Logger {
  return object : Logger {
    override fun debug(message: String) {
      console.log(message)
    }

    override fun info(message: String) {
      console.log(message)
    }

    override fun warn(message: String) {
      console.log(message)
    }

    override fun error(message: String) {
      console.log(message)
    }

    override fun error(message: String, throwable: Throwable) {
      console.log(message)
    }
  }
}