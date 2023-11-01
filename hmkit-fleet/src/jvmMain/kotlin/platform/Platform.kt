package platform

import com.highmobility.hmkitfleet.HMKitFleet
import org.slf4j.LoggerFactory
import java.util.UUID

actual val currentPlatform = KotlinPlatform.JVM

actual fun uuid(): String {
  return UUID.randomUUID().toString()
}

// Logging

actual fun createLogger(): Logger {
  val logger = LoggerFactory.getLogger(HMKitFleet::class.java)
  return object : Logger {
    override fun debug(message: String) {
      logger.debug(message)
    }

    override fun info(message: String) {
      logger.info(message)
    }

    override fun warn(message: String) {
      logger.warn(message)
    }

    override fun error(message: String) {
      logger.error(message)
    }

    override fun error(message: String, throwable: Throwable) {
      logger.error(message, throwable)
    }
  }
}