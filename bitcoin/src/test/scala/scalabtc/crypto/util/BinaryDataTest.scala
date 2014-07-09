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
}
