package scalabtc.crypto.encoding

import scalabtc.crypto.util.BinaryData

class Base58Test extends TextEncoderTest {

  val sampleData = BinaryData.fromHexString(
    "800C28FCA386C7A227600B2FE50B7CAE11EC86D3BF1FBE471BE89827E19D72AA1D507A5B8D")
  val expectedEncoded = "5HueCGU8rMjxEXxiPuD5BDku4MkFqeZyd4dZ1jvhTVqvbTLvyTJ"
  val invalidEncoded = "00000"

  "Base-58 encoder" must behave like validEncoder(
    Base58, sampleData, expectedEncoded, invalidEncoded)
}
