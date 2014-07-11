package scalabtc.crypto.encoding

import scalabtc.crypto.util.BinaryData

class Base16Test extends TextEncoderTest {

  val sampleData = BinaryData(BigInt(2114863597))
  val expectedEncoded = "7E0E41ED"
  val invalidEncoded = "1234ZXY"

  "Base-16 encoder" must behave like validEncoder(
    Base16, sampleData, expectedEncoded, invalidEncoded)
}
