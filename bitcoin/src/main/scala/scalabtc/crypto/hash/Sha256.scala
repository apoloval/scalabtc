package scalabtc.crypto.hash

import java.security.MessageDigest

object Sha256 {

  def createDigest(): MessageDigest = MessageDigest.getInstance("SHA-256")

  class Hash(val bytes: Array[Byte]) {

    require(bytes.length == 32,
      s"SHA256 hash requires a 256-bits (32 bytes) byte array (${bytes.length} given")

    override def equals(other: Any) = other match {
      case h: Hash => bytes.toSeq == h.bytes.toSeq
    }

    override def hashCode(): Int = bytes
      .takeRight(4)             // take the last 4 bytes...
      .map(_ & 0xFF)            // ...remove garbage bits...
      .zip(Seq(0, 8, 16, 24))   // ...zip with the left shift we want for each one...
      .map(z => z._1 << z._2)   // ...shift each byte...
      .reduce(_ | _)            // ...and calculate its binary OR: voila!
  }

  def apply(contents: Array[Byte]): Hash = new Hash(createDigest().digest(contents))

  def double(contents: Array[Byte]): Hash = {
    val digest = createDigest()
    digest.update(contents, 0, contents.length)
    val first = digest.digest()
    val second = digest.digest(first)
    new Hash(second)
  }
}
