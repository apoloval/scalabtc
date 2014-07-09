package scalabtc.crypto.hash

import scalabtc.crypto.util.BinaryData

class Hash[H <: HashAlgorithm](val algorithm: H, val data: BinaryData) {

  require(algorithm.isValidHashLength(data.length),
    s"invalid hash bytes length for ${algorithm.name}(${data.length} given")


  override def toString: String = s"${algorithm.name}{${data.toHexString}}"

  override def equals(other: Any) = other match {
    case h: Hash[H] => data == h.data
  }

  override def hashCode() = data.hashCode()
}
