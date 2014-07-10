package scalabtc.crypto

import org.scalatest.Matchers

import scalabtc.crypto.util.BinaryData

class PrivateKeyTest extends org.scalatest.FlatSpec with Matchers {

  val sampleKey = new PrivateKey(BinaryData.fromHexString(
    "0C28FCA386C7A227600B2FE50B7CAE11EC86D3BF1FBE471BE89827E19D72AA1D"))
  val invalidDataLength = BinaryData.fromHexString(
    "0C28FCA386C7A227600B2FE50B7CAE11EC86D3BF1FBE")
  val tooLowData = BinaryData.fromHexString(
    "0000000000000000000000000000000000000000000000000000000000000000")
  val tooHighData = BinaryData.fromHexString(
    "fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364142")
  val expectedPublicKey = new PublicKey(BinaryData.fromHexString("04" +
    "d0de0aaeaefad02b8bdc8a01a1b8b11c696bd3d66a2c5f10780d95b7df42645c" +
    "d85228a6fb29940e858e7e55842ae2bd115d1ed7cc0e82d934e929c97648cb0a"))
  val expectedWif = BinaryData.fromHexString(
    "800C28FCA386C7A227600B2FE50B7CAE11EC86D3BF1FBE471BE89827E19D72AA1D507A5B8D")
  val invalidChecksumWif = BinaryData.fromHexString(
    "800C28FCA386C7A227600B2FE50B7CAE11EC86D3BF1FBE471BE89827E19D72AA1DABCDEF01")
  val invalidPrefixWif = BinaryData.fromHexString(
    "AB0C28FCA386C7A227600B2FE50B7CAE11EC86D3BF1FBE471BE89827E19D72AA1D507A5B8D")

  "Private key" must "fail to be created with invalid data length" in {
    an[IllegalArgumentException] should be thrownBy { new PrivateKey(invalidDataLength) }
  }

  it must "fail to be created with too low value" in {
    an[IllegalArgumentException] should be thrownBy { new PrivateKey(tooLowData) }
  }

  it must "fail to be created with too high value" in {
    an[IllegalArgumentException] should be thrownBy { new PrivateKey(tooHighData) }
  }

  it must "generate its public key" in {
    sampleKey.publicKey should be (expectedPublicKey)
  }

  it must "generate its WIF" in {
    sampleKey.toWif(MainNetworkId) should be (expectedWif)
  }

  it must "be created from WIF" in {
    PrivateKey.fromWif(expectedWif, Some(MainNetworkId)) should be (sampleKey)
  }

  it must "fail to be created from WIF with invalid checksum" in {
    an[IllegalArgumentException] should be thrownBy {
      PrivateKey.fromWif(invalidChecksumWif, Some(MainNetworkId))
    }
  }

  it must "fail to be created from WIF with invalid prefix" in {
    an[IllegalArgumentException] should be thrownBy {
      PrivateKey.fromWif(invalidPrefixWif, Some(MainNetworkId))
    }
  }
}
