package platform

expect class Crypto() {
  /**
   * @param jwt the jwt with two parts separated by dots
   * @param privateKey the "private_key" value from the downloaded service account json file.
   *  Starts with -----BEGIN PRIVATE KEY
   *
   * @return the signature of the jwt
   */
  fun signJWTJs(header: String, payload: String, privateKey: String): String

  fun signJWTJvm(jwt: String, privateKey: String): ByteArray

}