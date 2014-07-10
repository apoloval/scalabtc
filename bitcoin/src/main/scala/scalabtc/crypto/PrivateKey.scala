package scalabtc.crypto

import scala.util.Try

import scalabtc.crypto.ec.ECDSA
import scalabtc.crypto.hash.{Sha256, Sha256Hash}
import scalabtc.crypto.util.BinaryData

class PrivateKey(val data: BinaryData) {

  require(isValidLength, "cannot create private key from data with " +
    "length ${data.length} (${PrivateKey.DataLength} expected})")

  require(ECDSA.isValidPrivateKeyRange(data),
    s"cannot create private key from data out of secp256k1 range $data")

  lazy val publicKey: PublicKey = new PublicKey(
    ECDSA.createPublicKey(data, isCompressed = false))

  override def hashCode() = data.hashCode()

  override def equals(other: Any) = other match {
    case otherKey: PrivateKey => otherKey.data == data
    case _ => false
  }

  override def toString = s"private key {$data}"

  def toWif(network: NetworkId): BinaryData = {
    val extendedKey = network.wifPrefix +: data
    val checksum = Sha256.double(extendedKey).data.take(4)
    extendedKey ++ checksum
  }

  def this() = this(ECDSA.createPrivateKey())

  def sign(input: Sha256Hash): Signature = ECDSA.sign(input, data)

  def sign(contents: BinaryData): Signature = ECDSA.sign(Sha256(contents), data)

  private def isValidLength = data.length == PrivateKey.DataLength
}

object PrivateKey {

  val DataLength = 32
  val WipFormatLength = 37

  def fromWif(data: BinaryData, expectedNetwork: Option[NetworkId]): PrivateKey = {
    require(data.length == WipFormatLength,
      s"invalid WIP data: it must have $WipFormatLength bytes (${data.length} given)")
    expectedNetwork.foreach { n =>
      val prefix = data.head
      require(n.wifPrefix == prefix,
        s"network prefix in WIP ($prefix) doesn't match expected one (${n.wifPrefix})")
    }
    val extendedKey = data.dropRight(4)
    val actualChecksum = data.takeRight(4)
    val expectedChecksum = Sha256.double(extendedKey).data.take(4)
    require(actualChecksum == expectedChecksum,
      s"invalid WIP data: invalid checksum $actualChecksum ($expectedChecksum expected)")
    new PrivateKey(extendedKey.drop(1))
  }

  def isValidWif(data: BinaryData, expectedNetwork: Option[NetworkId]): Boolean =
    Try(fromWif(data, expectedNetwork)).isSuccess
}
