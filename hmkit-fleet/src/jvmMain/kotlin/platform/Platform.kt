package platform

actual val currentPlatform = KotlinPlatform.JVM

actual fun uuid(): String {
    return java.util.UUID.randomUUID().toString()
}