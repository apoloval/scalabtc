package scalabtc.crypto.util

import org.scalatest.{Matchers, FlatSpec}

class BinaryDataTest extends FlatSpec with Matchers {

  val d1 = BinaryData(Seq[Byte](1, 3, 5).toArray)
  val d2 = BinaryData(Seq[Byte](1, 3, 5).toArray)
  val d3 = BinaryData(Seq[Byte](1, 3, 5, 7).toArray)
  val d4 = BinaryData(Seq[Byte](1, 3, 4).toArray)

  "Binary data" must "honour equals" in {
    d1 should be (d2)
    d1 should not be (d3)
    d1 should not be (d4)
  }

  it must "honour hashCode" in {
    d1.hashCode() should be (d2.hashCode())
    d1.hashCode() should not be (d3.hashCode())
    d1.hashCode() should not be (d4.hashCode())
  }

  it must "be converted into big int" in {
    d1.toBigInt should be (BigInt(66309))
  }

  it must "be converted into hex string" in {
    d1.toHexString should be ("010305")
  }

  it must "be converted from hex string" in {
    BinaryData.fromHexString("010305") should be (d1)
    BinaryData.fromHexString("10305") should be (d1)
  }

  it must "fail to be converted from invalid hex string" in {
    an[IllegalArgumentException] should be thrownBy { BinaryData.fromHexString("I'm YOUR father") }
    an[IllegalArgumentException] should be thrownBy { BinaryData.fromHexString("1030X") }
    an[IllegalArgumentException] should be thrownBy { BinaryData.fromHexString("01 03 05") }
  }

  it must "concatenate with another binary data object" in {
    d1 ++ d4 should be (BinaryData(Array[Byte](1, 3, 5, 1, 3, 4)))
  }

  it must "append a new byte" in {
    d1 :+ 10 should be (BinaryData(Array[Byte](1, 3, 5, 10)))
  }

  it must "prepend a new byte" in {
    (10: Byte) +: d1 should be (BinaryData(Array[Byte](10, 1, 3, 5)))
  }

  it must "take n first bytes" in {
    d1.take(2) should be (BinaryData(Array[Byte](1, 3)))
  }

  it must "drop n first bytes" in {
    d1.drop(2) should be (BinaryData(Array[Byte](5)))
  }
}
