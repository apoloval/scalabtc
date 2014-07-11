package scalabtc.crypto.encoding

import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.control.NonFatal

import scalabtc.crypto.util.BinaryData

abstract class TextEncoder(alphabet: String, isCaseSensitive: Boolean) {

  def name: String

  private val symbols = if (isCaseSensitive) alphabet else alphabet.toUpperCase
  private val base = BigInt(symbols.length)
  private val index: Seq[Byte] = {
    val result = mutable.IndexedSeq.fill(256)(-1.toByte)
    for (s <- symbols.zipWithIndex) {
      result.update(s._1, s._2.toByte)
    }
    result.toSeq
  }

  def encode(data: BinaryData): String = encode(data.toUnsignedBigInt, "")

  def decode(data: String): BinaryData = try {
    val input = if (isCaseSensitive) data else data.toUpperCase
    val decodedValue = input.reverse.zipWithIndex.map { s =>
      val symbolValue = BigInt(decode(s._1))
      val shift = base.pow(s._2)
      shift * symbolValue
    }.sum
    BinaryData(decodedValue).dropWhile(_ == 0)
  } catch {
    case NonFatal(e) => throw new IllegalArgumentException(
      s"cannot decode string value $data with $name encoder", e)
  }

    @tailrec
    private def encode(data: BigInt, buffer: String): String =
    if (data == BigInt(0)) buffer
    else encode(data / base, encode((data % base).toByte) + buffer)

  private def encode(b: Byte): Char = symbols(b)

  private def decode(s: Char): Byte = {
    val value = index(s)
    require(value != -1, s"cannot decode symbol $s according to alphabet $symbols")
    value
  }
}
