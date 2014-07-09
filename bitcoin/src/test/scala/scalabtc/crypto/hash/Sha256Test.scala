package scalabtc.crypto.hash

import javax.xml.bind.DatatypeConverter
import org.scalatest.{FlatSpec, Matchers}
import scalabtc.crypto.util.BinaryData

class Sha256Test extends FlatSpec with Matchers {

  val message = "Lorem ipsum ad his scripta blandit partiendo"
  val messageBytes = BinaryData(message.getBytes("UTF-8"))
  val otherMessage = "A long time ago in a galaxy far, far away..."
  val otherMessageBytes = BinaryData(otherMessage.getBytes("UTF-8"))
  val expectedHash = BinaryData(DatatypeConverter.parseHexBinary(
    "0122de6992c18bc2b7b1d1b0382f609cca2ce459b7fe3f0dae750516917f4a88"))
  val expectedDoubleHash = BinaryData(DatatypeConverter.parseHexBinary(
    "3a9f70f90211425bd0204acd143d328c433a750562b36150a7f04b69342bb863"))

  "SHA256" must "create a valid hash of a message" in {
    Sha256(message).data should be (expectedHash)
  }

  it must "create a valid double hash of a message" in {
    Sha256.double(messageBytes).data should be (expectedDoubleHash)
  }

  it must "import a hash from its bytes" in {
    Sha256.fromBytes(expectedHash) should be (Sha256(message))
  }

  it must "fail to import a hash from invalid bytes" in {
    an [IllegalArgumentException] should be thrownBy { Sha256.fromBytes(messageBytes) }
  }

  "SHA256 hash" must "be equals to the same hash" in {
    Sha256(message) should be (Sha256(message))
  }

  it must "not be equals to a different hash" in {
    Sha256(message) should not (be (Sha256.fromBytes(expectedDoubleHash)))
  }

  it must "have different hash codes for different hash instances" in {
    Sha256(messageBytes).hashCode() should not (be(Sha256(otherMessageBytes).hashCode()))
  }
}
