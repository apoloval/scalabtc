package scalabtc.crypto.util

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
}

object BinaryData {

  def apply(number: BigInt): BinaryData = new BinaryData(number.toByteArray)

  def apply(bytes: Array[Byte]): BinaryData = new BinaryData(bytes)
}
