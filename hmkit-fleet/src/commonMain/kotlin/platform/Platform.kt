package platform

enum class KotlinPlatform {
  JVM, JS
}

expect val currentPlatform: KotlinPlatform

expect fun uuid(): String