package scalabtc.crypto.util

import scala.util.control.NonFatal

class BinaryData(bytes: Array[Byte]) {

  val length: Int = bytes.length

  val toByteArray: Array[Byte] = bytes

  lazy val toBigInt: BigInt = BigInt(bytes)

  lazy val toHexString: String = bytes.map { b =>
    val s = Integer.toHexString(b & 0xFF)
    if (s.size < 2) s"0$s" else s
  }.mkString

  override def equals(other: Any): Boolean = other match {
    case data: BinaryData =>
      val theirBytes = data.toByteArray
      theirBytes.length == bytes.length && theirBytes.zip(bytes).forall(pair => pair._1 == pair._2)
    case _ => false
  }

  override def hashCode(): Int = toBigInt.hashCode()


  override def toString = toHexString

  def ++ (otherData: BinaryData): BinaryData = new BinaryData(bytes ++ otherData.toByteArray)
  def :+ (byte: Byte): BinaryData = new BinaryData(bytes ++ Array(byte))
  def +: (byte: Byte): BinaryData = new BinaryData(Array(byte) ++ bytes)

  def take(n: Int): BinaryData = new BinaryData(bytes.take(n))
  def drop(n: Int): BinaryData = new BinaryData(bytes.drop(n))
}

object BinaryData {

  def apply(number: BigInt): BinaryData = new BinaryData(number.toByteArray)

  def apply(bytes: Array[Byte]): BinaryData = new BinaryData(bytes)

  def fromHexString(hexString: String): BinaryData = {
    def parseHex(str: String): Byte = Integer.parseInt(str, 16).toByte

    val normHexString = if (hexString.length % 2 == 0) hexString else s"0$hexString"
    try { BinaryData(normHexString.grouped(2).map(parseHex).toArray) }
    catch {
      case NonFatal(e) =>
        throw new IllegalArgumentException(
          s"cannot convert hex string $hexString into a binary data object", e)
    }
  }
}
