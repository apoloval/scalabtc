package scalabtc.crypto

import org.scalatest.Matchers

import scalabtc.crypto.util.BinaryData

class PrivateKeyTest extends org.scalatest.FlatSpec with Matchers {

  val sampleKey = new PrivateKey(BinaryData.fromHexString(
    "18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725"))
  val expectedPublicKey = new PublicKey(BinaryData.fromHexString("04" +
    "50863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B2352" +
    "2CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6"))

  "Private key" must "generate its public key" in {
    sampleKey.publicKey should be (expectedPublicKey)
  }
}
