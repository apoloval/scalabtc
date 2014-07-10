package scalabtc.crypto

sealed trait NetworkId {
  val addressPrefix: Byte
  val wifPrefix: Byte
}

case object MainNetworkId extends NetworkId {
  override val addressPrefix = 0x00.toByte
  override val wifPrefix = 0x80.toByte
}

case object TestNetworkId extends NetworkId {
  override val addressPrefix = 0x6f.toByte
  override val wifPrefix = 0xef.toByte
}

case object NamecoinNetworkId extends NetworkId {
  override val addressPrefix = 0x34.toByte
  override val wifPrefix = 0xef.toByte
}
