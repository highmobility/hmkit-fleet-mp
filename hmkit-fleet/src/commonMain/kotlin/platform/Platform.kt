package platform

enum class KotlinPlatform {
  JVM, JS
}

expect val currentPlatform: KotlinPlatform

expect fun uuid(): String

// Logger

interface Logger {
  fun debug(message: String)
  fun info(message: String)
  fun warn(message: String)
  fun error(message: String)
  fun error(message: String, throwable: Throwable)
}

expect fun createLogger(): Logger