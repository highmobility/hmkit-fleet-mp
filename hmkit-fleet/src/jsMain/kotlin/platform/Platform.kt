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