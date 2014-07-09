package scalabtc.crypto

import scalabtc.crypto.ec.ECDSA
import scalabtc.crypto.hash.{Sha256Hash, Sha256}
import scalabtc.crypto.util.BinaryData

class PrivateKey(data: BinaryData) {

  lazy val publicKey: PublicKey = new PublicKey(
    ECDSA.createPublicKey(data, isCompressed = false))

  lazy val compressedPublicKey: PublicKey = new PublicKey(
    ECDSA.createPublicKey(data, isCompressed = true))

  def this() = this(ECDSA.createPrivateKey())

  def sign(input: Sha256Hash): Signature = ECDSA.sign(input, data)

  def sign(contents: BinaryData): Signature = ECDSA.sign(Sha256(contents), data)
}
