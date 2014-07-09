package scalabtc.crypto.hash

import java.security.MessageDigest

import scalabtc.crypto.util.BinaryData

object Sha256 extends HashAlgorithm {

  override val name = "SHA-256"

  override def isValidHashLength(length: Int) = length == 32

  override def apply(contents: BinaryData) = new Hash(
    algorithm = this,
    data = BinaryData(createDigest().digest(contents.toByteArray)))

  override def fromBytes(hashData: BinaryData) = new Hash(this, hashData)

  def double(contents: BinaryData): Sha256Hash = {
    val digest = createDigest()
    digest.update(contents.toByteArray, 0, contents.length)
    val first = digest.digest()
    val second = digest.digest(first)
    new Hash(this, BinaryData(second))
  }

  private def createDigest() = MessageDigest.getInstance("SHA-256")

}
