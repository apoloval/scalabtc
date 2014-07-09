package scalabtc.crypto.hash

import scalabtc.crypto.util.BinaryData

class Hash[H <: HashAlgorithm](val algorithm: H, val data: BinaryData) {

  require(algorithm.isValidHashLength(data.length),
    s"invalid hash bytes length for ${algorithm.name}(${data.length} given")

  override def equals(other: Any) = other match {
    case h: Hash[H] => data == h.data
  }

  override def hashCode(): Int = data.toByteArray
    .takeRight(4)             // take the last 4 bytes...
    .map(_ & 0xFF)            // ...remove garbage bits...
    .zip(Seq(0, 8, 16, 24))   // ...zip with the left shift we want for each one...
    .map(z => z._1 << z._2)   // ...shift each byte...
    .reduce(_ | _)            // ...and calculate its binary OR: voila!
}
