package scalabtc.crypto.encoding

object Base58 extends TextEncoder(
  alphabet = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz",
  isCaseSensitive = true) {

  override val name = "BASE-58"
}
