package scalabtc.crypto.hash

import javax.xml.bind.DatatypeConverter

import scalabtc.crypto.util.BinaryData

class Ripemd160Test extends HashTest(Ripemd160) {

  val messageExpectedHash = BinaryData(DatatypeConverter.parseHexBinary(
    "ee3d89d9e6ce25abba288bcc1e1320dbaa35eae5"))
  val otherMessageExpectedHash = BinaryData(DatatypeConverter.parseHexBinary(
    "7de5950c3a5068d3c6a3f05400fc990ade9eba99"))
}
