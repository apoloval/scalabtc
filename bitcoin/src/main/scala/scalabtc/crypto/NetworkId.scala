package scalabtc.crypto

sealed trait NetworkId {
  val byte: Byte
}

case object MainNetworkId extends NetworkId { override val byte: Byte = 0x00 }
case object TestNetworkId extends NetworkId { override val byte: Byte = 0x6f }
case object NamecoinNetworkId extends NetworkId { override val byte: Byte = 0x34 }
