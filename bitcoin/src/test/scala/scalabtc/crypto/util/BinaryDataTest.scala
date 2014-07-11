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
    d1.toHexString should be ("10305")
  }

  it must "be converted into base58 string" in {
    d1.toBase58String should be ("LiG")
  }

  it must "be converted from hex string" in {
    BinaryData.fromHexString("010305") should be (d1)
    BinaryData.fromHexString("10305") should be (d1)
  }

  it must "be converted from base58 string" in {
    BinaryData.fromBase58String("LiG") should be (d1)
    BinaryData.fromBase58String("1LiG") should be (d1)
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

  it must "retrieve head" in {
    d1.head should be (1.toByte)
  }

  it must "take n first bytes" in {
    d1.take(2) should be (BinaryData(Array[Byte](1, 3)))
  }

  it must "take right n last bytes" in {
    d1.takeRight(2) should be (BinaryData(Array[Byte](3, 5)))
  }

  it must "drop n first bytes" in {
    d1.drop(2) should be (BinaryData(Array[Byte](5)))
  }

  it must "drop right n last bytes" in {
    d1.dropRight(2) should be (BinaryData(Array[Byte](1)))
  }

  it must "drop while bytes" in {
    d1.dropWhile(b => b == 1) should be (BinaryData(Array[Byte](3, 5)))
  }
}
