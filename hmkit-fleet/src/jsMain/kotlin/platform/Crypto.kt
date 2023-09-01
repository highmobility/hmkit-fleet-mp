package platform

actual class Crypto {
  /**
   * @param jwt the jwt with two parts separated by dots. not base64 encode
   * @param privateKey the "private_key" value from the downloaded service account json file.
   *  Starts with -----BEGIN PRIVATE KEY
   *
   * @return the signature of the jwt
   */
  actual fun signJWTJs(header: String, payload: String, privateKey: String): String {
    val alg = "ES256"
    val headerJSON = JSON.parse<dynamic>(header)
    val bodyJSON = JSON.parse<dynamic>(payload)

    val jwt = jsrsasign.KJUR.jws.JWS.sign(alg, headerJSON, bodyJSON, privateKey)
    return jwt
  }

  actual fun signJWTJvm(jwt: String, privateKey: String): ByteArray {
    return byteArrayOf()
  }
}

fun String.decodeHex(): ByteArray {
  check(length % 2 == 0) { "Must have an even length" }

  return chunked(2)
    .map { it.toInt(16).toByte() }
    .toByteArray()
}

@JsModule("jsrsasign")
@JsNonModule
external object jsrsasign {
  object KJUR {
    object jws {
      object JWS {
        fun sign(alg: String, header: dynamic, payload: dynamic, private_key: String): dynamic
      }
    }
  }
}
