package scalabtc.crypto

import scalabtc.crypto.hash.{Ripemd160, Sha256}
import scalabtc.crypto.util.BinaryData

class PublicKey(val data: BinaryData) {

  require(isValidDataLength, "public key length must be one of " +
    s" ${PublicKey.AllowedDataLength.mkString(", ")} (${data.length} given)")

  require(isValidDataPrefix, s"public key prefix $dataPrefix is not valid for data $data")

  def toAddress(network: NetworkId): Address = {
    val baseHash = network.byte +: Ripemd160(Sha256(data)).data
    val checksum = Sha256.double(baseHash).data.take(4)
    new Address(baseHash ++ checksum)
  }

  lazy val isCompressed: Boolean = data.length == PublicKey.CompressedDataLength

  override val toString = data.toHexString

  override def equals(other: Any) = other match {
    case otherKey: PublicKey => otherKey.data == data
  }

  override def hashCode() = data.hashCode()

  private def isValidDataLength = PublicKey.AllowedDataLength.contains(data.length)

  private def isValidDataPrefix = data.length match {
    case PublicKey.UncompressedDataLength => dataPrefix == PublicKey.UncompressedDataPrefix
    case _ => false
  }

  private def dataPrefix = data.toByteArray.head
}

object PublicKey {

  val CompressedDataLength = 33
  val UncompressedDataLength = 65
  val AllowedDataLength = Seq(CompressedDataLength, UncompressedDataLength)

  val UncompressedDataPrefix = 0x04.toByte
}
