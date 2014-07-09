package scalabtc.crypto

package object hash {

  type Sha256Hash = Hash[Sha256.type]
  type Ripemd160Hash = Hash[Ripemd160.type]
}
