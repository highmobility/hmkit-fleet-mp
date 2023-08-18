import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.jvm.JvmField

/**
 * The Fleet SDK environment.
 */
@OptIn(ExperimentalJsExport::class)
@JsExport
enum class Environment {
    PRODUCTION, SANDBOX;

    internal val url: String
        get() {
            return webUrl
                ?: when (this) {
                    PRODUCTION -> prodUrl
                    SANDBOX -> "https://sandbox.api.high-mobility.com/v1"
                }
        }

    companion object {
        private const val prodUrl = "https://api.high-mobility.com/v1"

        /**
         * Override the web url, which is normally derived from the [HMKitFleet.environment]
         * value
         */
        @JvmField
        var webUrl: String? = null
    }
}