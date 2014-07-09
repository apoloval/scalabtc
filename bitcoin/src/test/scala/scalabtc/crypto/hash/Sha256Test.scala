package scalabtc.crypto.hash

import javax.xml.bind.DatatypeConverter

import scalabtc.crypto.util.BinaryData

class Sha256Test extends HashTest(Sha256) {

  val messageExpectedHash = BinaryData(DatatypeConverter.parseHexBinary(
    "0122de6992c18bc2b7b1d1b0382f609cca2ce459b7fe3f0dae750516917f4a88"))
  val otherMessageExpectedHash = BinaryData(DatatypeConverter.parseHexBinary(
    "d10879ae177056197abf97c47a0119c6d24e2c9cf33865250d3b41187867081e"))
  val expectedMessageDoubleHash = BinaryData(DatatypeConverter.parseHexBinary(
    "3a9f70f90211425bd0204acd143d328c433a750562b36150a7f04b69342bb863"))

  it must "create a valid double hash of a message" in {
    Sha256.double(messageBytes).data should be (expectedMessageDoubleHash)
  }
}
