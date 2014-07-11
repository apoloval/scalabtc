package scalabtc.crypto.encoding

object Base16 extends TextEncoder(
  alphabet = "0123456789ABCDEF",
  isCaseSensitive = false) {

  override val name = "BASE-16"
}
