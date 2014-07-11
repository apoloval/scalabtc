package scalabtc.crypto.util

import scala.util.control.NonFatal
import scalabtc.crypto.encoding.{Base58, Base16}

class BinaryData(bytes: Array[Byte]) {

  val length: Int = bytes.length

  val toByteArray: Array[Byte] = bytes

  lazy val toBigInt: BigInt = BigInt(bytes)

  lazy val toUnsignedBigInt: BigInt = BigInt(1, bytes)

  lazy val toHexString: String = Base16.encode(this)

  lazy val toBase58String: String = Base58.encode(this)

  override def equals(other: Any): Boolean = other match {
    case data: BinaryData => data.toBigInt == toBigInt
    case _ => false
  }

  override def hashCode(): Int = toBigInt.hashCode()


  override def toString = toHexString

  def ++ (otherData: BinaryData): BinaryData = new BinaryData(bytes ++ otherData.toByteArray)
  def :+ (byte: Byte): BinaryData = new BinaryData(bytes ++ Array(byte))
  def +: (byte: Byte): BinaryData = new BinaryData(Array(byte) ++ bytes)

  def head: Byte = bytes.head
  def take(n: Int): BinaryData = new BinaryData(bytes.take(n))
  def takeRight(n: Int): BinaryData = new BinaryData(bytes.takeRight(n))
  def drop(n: Int): BinaryData = new BinaryData(bytes.drop(n))
  def dropRight(n: Int): BinaryData = new BinaryData(bytes.dropRight(n))
  def dropWhile(f: Byte => Boolean) = new BinaryData(bytes.dropWhile(f))
}

object BinaryData {

  def apply(number: BigInt): BinaryData = new BinaryData(number.toByteArray)

  def apply(bytes: Array[Byte]): BinaryData = new BinaryData(bytes)

  def fromHexString(hexString: String): BinaryData = Base16.decode(hexString)

  def fromBase58String(base58String: String): BinaryData = Base58.decode(base58String)
}
