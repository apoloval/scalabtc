package scalabtc.crypto.encoding

import org.scalatest.{Matchers, FlatSpec}

import scalabtc.crypto.util.BinaryData

trait TextEncoderTest extends FlatSpec with Matchers {

  def validEncoder(encoder: TextEncoder,
                   sampleData: BinaryData,
                   expectedEncoded: String,
                   invalidEncoded: String): Unit = {
    it must "encode" in {
      encoder.encode(sampleData) should be(expectedEncoded)
    }

    it must "decode" in {
      encoder.decode(expectedEncoded) should be(sampleData)
    }

    it must "fail to encode invalid encoded string" in {
      an[IllegalArgumentException] should be thrownBy { encoder.decode(invalidEncoded) }
    }
  }
}
