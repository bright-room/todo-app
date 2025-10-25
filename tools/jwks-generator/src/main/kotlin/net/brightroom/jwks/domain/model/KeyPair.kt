package net.brightroom.jwks.domain.model

import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

/**
 * キーペア
 */
data class KeyPair constructor(val publicKey: PublicKey, val privateKey: PrivateKey) {
    companion object {
        fun create(): KeyPair {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(4096, SecureRandom())
            val keyPair = keyPairGenerator.generateKeyPair()

            val publicKey = keyPair.public as RSAPublicKey
            val privateKey = keyPair.private as RSAPrivateKey

            return KeyPair(PublicKey(publicKey), PrivateKey(privateKey))
        }
    }
}
