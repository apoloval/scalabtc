package scalabtc.crypto

import scalabtc.crypto.ec.ECDSA
import scalabtc.crypto.hash.{Ripemd160, Sha256}
import scalabtc.crypto.util.BinaryData

class PublicKey(val data: BinaryData) {

  require(isValidDataLength, "public key length must be one of " +
    s" ${PublicKey.AllowedDataLength.mkString(", ")} (${data.length} given)")

  require(isValidDataPrefix, s"public key prefix $dataPrefix is not valid for data $data")

  lazy val isCompressed: Boolean = data.length == PublicKey.CompressedDataLength

  lazy val toCompressed: PublicKey =
    if (isCompressed) this else new PublicKey(ECDSA.compressPublicKey(data))

  override val toString = data.toHexString

  override def equals(other: Any) = other match {
    case otherKey: PublicKey => otherKey.data == data
  }

  override def hashCode() = data.hashCode()

  def toAddress(network: NetworkId): Address = {
    val baseHash = network.addressPrefix +: Ripemd160(Sha256(data)).data
    val checksum = Sha256.double(baseHash).data.take(4)
    new Address(baseHash ++ checksum)
  }

  private def isValidDataLength = PublicKey.AllowedDataLength.contains(data.length)

  private def isValidDataPrefix = data.length match {
    case PublicKey.UncompressedDataLength => PublicKey.UncompressedDataPrefixes.contains(dataPrefix)
    case PublicKey.CompressedDataLength => PublicKey.CompressedDataPrefixes.contains(dataPrefix)
    case _ => false
  }

  private def dataPrefix = data.toByteArray.head
}

object PublicKey {

  val CompressedDataLength = 33
  val UncompressedDataLength = 65
  val AllowedDataLength = Seq(CompressedDataLength, UncompressedDataLength)

  val UncompressedDataPrefixes = Seq(0x04.toByte)
  val CompressedDataPrefixes = Seq(0x02.toByte, 0x03.toByte)
}
