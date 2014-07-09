package scalabtc.crypto.hash

import org.spongycastle.crypto.digests.RIPEMD160Digest

import scalabtc.crypto.util.BinaryData

object Ripemd160 extends HashAlgorithm {

  override val name = "RIPEMD-256"

  override def isValidHashLength(length: Int) = length == 20

  override def apply(contents: BinaryData) = {
    val digest = createDigest()
    val out = new Array[Byte](20)

    digest.update(contents.toByteArray, 0, contents.length)
    digest.doFinal(out, 0)

    new Hash(algorithm = this, data = BinaryData(out))
  }

  override def fromBytes(hashData: BinaryData) = new Hash(this, hashData)

  private def createDigest() = new RIPEMD160Digest()

}
