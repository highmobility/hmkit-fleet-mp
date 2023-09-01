package platform

import com.highmobility.crypto.Crypto
import com.highmobility.crypto.value.PrivateKey

actual class Crypto {
  /**
   * @param jwt the jwt with two parts separated by dots
   * @param privateKey the "private_key" value from the downloaded service account json file.
   *  Starts with -----BEGIN PRIVATE KEY
   *
   * @return the signature of the jwt
   */
  actual fun signJWTJvm(jwt: String, privateKey: String): ByteArray {
    val crypto = Crypto()
    val hmPrivate = PrivateKey(privateKey, PrivateKey.Format.PKCS8)
    return crypto.signJWT(jwt.toByteArray(), hmPrivate).byteArray
  }

  actual fun signJWTJs(header: String, payload: String, privateKey: String): String {
    return ""
  }
}