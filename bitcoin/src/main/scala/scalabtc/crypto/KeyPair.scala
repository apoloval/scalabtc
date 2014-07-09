package scalabtc.crypto

/** A pair of public and private keys. */
case class KeyPair(publicKey: PublicKey, privateKey: PrivateKey) {

  /** Calculate the address of this keypair in the given network.  */
  def toAddress(network: NetworkId): Address = publicKey.toAddress(network)
}
