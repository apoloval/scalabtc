package scalabtc.crypto

import org.scalatest.{Matchers, FlatSpec}

import scalabtc.crypto.util.BinaryData

class PublicKeyTest extends FlatSpec with Matchers {

  val samplekey = new PublicKey(BinaryData.fromHexString("04" +
    "50863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B2352" +
    "2CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6"))
  val expectedPublicAddress = BinaryData.fromHexString(
    "00010966776006953D5567439E5E39F86A0D273BEED61967F6")

  "Public key" must "fail to instantiate with invalid data length" in {
    an[IllegalArgumentException] should be thrownBy {
      new PublicKey(BinaryData.fromHexString("0450863AD64A87AE8A2FE83C1AF1A8403C"))
    }
  }

  it must "fail to instantiate with invalid data prefix" in {
    an[IllegalArgumentException] should be thrownBy {
      new PublicKey(BinaryData.fromHexString("FF" +
        "50863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B2352" +
        "2CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6"))
    }
  }

  it must "report whether it is compressed" in {
    samplekey should not be ('compressed)
  }

  it must "generate its corresponding address" in {
    samplekey.toAddress(MainNetworkId).data should be(expectedPublicAddress)
  }
}
