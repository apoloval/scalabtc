package scalabtc.crypto

/** A pair of public and private keys. */
case class KeyPair(publicKey: PublicKey, privateKey: PrivateKey) {

  /** The address from this key pair. */
  lazy val address: Address = publicKey.address
}
