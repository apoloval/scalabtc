package scalabtc.crypto.ec

import java.security.SecureRandom

import org.spongycastle.asn1.sec.SECNamedCurves
import org.spongycastle.asn1.x9.X9ECParameters
import org.spongycastle.crypto.AsymmetricCipherKeyPair
import org.spongycastle.crypto.generators.ECKeyPairGenerator
import org.spongycastle.crypto.params.{ECDomainParameters, ECKeyGenerationParameters, ECPrivateKeyParameters}
import org.spongycastle.crypto.signers.ECDSASigner
import org.spongycastle.math.ec.{ECFieldElement, ECPoint}

import scalabtc.crypto.Signature
import scalabtc.crypto.hash.Sha256Hash
import scalabtc.crypto.util.BinaryData

private [crypto] object ECDSA {

  private val params: X9ECParameters = SECNamedCurves.getByName("secp256k1")
  private val curve: ECDomainParameters = new ECDomainParameters(
    params.getCurve, params.getG, params.getN, params.getH)
  private val halfCurveOrder = params.getN.shiftRight(1)
  private val secureRandom = new SecureRandom

  def isValidPrivateKeyRange(privateKey: BinaryData): Boolean = {
    val intData = privateKey.toBigInt
    intData > curve.getH && intData < curve.getN
  }

  def createPrivateKey(): BinaryData = {
    val keyPair = ECDSA.createKeyPair()
    val privateKeyParams = keyPair.getPrivate.asInstanceOf[ECPrivateKeyParameters]
    BinaryData(privateKeyParams.getD)
  }

  def createPublicKey(privateKey: BinaryData, isCompressed: Boolean): BinaryData = {
    var point: ECPoint = ECDSA.curve.getG.multiply(privateKey.toBigInt.bigInteger)
    if (isCompressed) {
      point = compressPoint(point)
    }
    BinaryData(point.getEncoded)
  }

  def compressPublicKey(publicKey: BinaryData): BinaryData =
    BinaryData(compressPoint(decodePoint(publicKey)).getEncoded)

  def sign(input: Sha256Hash, privateKey: BinaryData): Signature = {
    val signer = new ECDSASigner
    val privateKeyParams = new ECPrivateKeyParameters(privateKey.toBigInt.bigInteger, curve)
    signer.init(true, privateKeyParams)
    val components = signer.generateSignature(input.data.toByteArray)
    Signature(BinaryData(components(0)), BinaryData(components(1)))
  }

  private def compressPoint(uncompressed: ECPoint): ECPoint = {
    new ECPoint.Fp(curve.getCurve, uncompressed.getX, uncompressed.getY, true)
  }

  private def decodePoint(data: BinaryData): ECPoint =
    curve.getCurve.decodePoint(data.toByteArray)

  private def createGenerator(): ECKeyPairGenerator = {
    val generator = new ECKeyPairGenerator
    val keygenParams = new ECKeyGenerationParameters(curve, secureRandom)
    generator.init(keygenParams)
    generator
  }

  private def createKeyPair(): AsymmetricCipherKeyPair = {
    val generator = createGenerator()
    generator.generateKeyPair
  }
}
