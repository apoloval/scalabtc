package scalabtc.crypto.hash

import java.nio.charset.Charset

import scalabtc.crypto.util.BinaryData

trait HashAlgorithm {

  /** The name of the hash algorithm. */
  def name: String

  /** Indicate whether the given hash length is valid for this algorithm. */
  def isValidHashLength(length: Int): Boolean

  /** Create a new hash using this algorithm. */
  def apply(contents: BinaryData): Hash[this.type]

  /** Create a new hash of the given message using this algorithm. */
  def apply(message: String, charset: Charset = Charset.defaultCharset()): Hash[this.type] =
    apply(BinaryData(message.getBytes(charset)))

  /** Create a new hash by importing its bytes. */
  def fromBytes(hashData: BinaryData): Hash[this.type]
}
