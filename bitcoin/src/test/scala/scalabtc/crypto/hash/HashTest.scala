package scalabtc.crypto.hash

import org.scalatest.{FlatSpec, Matchers}

import scalabtc.crypto.util.BinaryData

abstract class HashTest(algorithm: HashAlgorithm) extends FlatSpec with Matchers {

  val message = "Lorem ipsum ad his scripta blandit partiendo"
  val messageBytes = BinaryData(message.getBytes("UTF-8"))
  val messageExpectedHash: BinaryData

  val otherMessage = "A long time ago in a galaxy far, far away..."
  val otherMessageBytes = BinaryData(otherMessage.getBytes("UTF-8"))
  val otherMessageExpectedHash: BinaryData

  algorithm.name must "create a valid hash of a message" in {
    algorithm(message).data should be (messageExpectedHash)
  }

  it must "import a hash from its bytes" in {
    algorithm.fromBytes(messageExpectedHash) should be (algorithm(message))
  }

  it must "fail to import a hash from invalid bytes" in {
    an [IllegalArgumentException] should be thrownBy { algorithm.fromBytes(messageBytes) }
  }

  s"${algorithm.name} hash" must "be equals to the same hash" in {
    algorithm(message) should be (algorithm(message))
  }

  it must "not be equals to a different hash" in {
    algorithm(message) should not (be (algorithm.fromBytes(otherMessageExpectedHash)))
  }

  it must "have different hash codes for different hash instances" in {
    algorithm(messageBytes).hashCode() should not (be(algorithm(otherMessageBytes).hashCode()))
  }

}
