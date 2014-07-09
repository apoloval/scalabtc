package scalabtc.crypto

import com.google.bitcoin.core.{Utils => BitcoinjUtils}

import scalabtc.crypto.util.BinaryData

class PublicKey(data: BinaryData) {

  lazy val address: Address = new Address(BitcoinjUtils.sha256hash160(data.toByteArray))

  lazy val isCompressed: Boolean = data.length == PublicKey.CompressedByteLength

  override val toString = data.toHexString
}

object PublicKey {

  val CompressedByteLength = 33
}
