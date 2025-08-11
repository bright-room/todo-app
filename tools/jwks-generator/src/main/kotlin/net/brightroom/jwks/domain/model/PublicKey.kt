package net.brightroom.jwks.domain.model

import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import kotlin.jvm.JvmInline

/** 公開鍵 */
@JvmInline
value class PublicKey(
    private val value: RSAPublicKey,
) {
    fun asPEM(): String {
        val encoded = value.encoded
        val base64 = Base64.getEncoder().encodeToString(encoded)
        return buildString {
            appendLine("-----BEGIN PUBLIC KEY-----")
            base64.chunked(64).forEach { appendLine(it) }
            appendLine("-----END PUBLIC KEY-----")
        }
    }

    fun asEncodedModulus(): String = base64UrlEncode(value.modulus.toByteArray())

    fun asEncodedExponent(): String = base64UrlEncode(value.publicExponent.toByteArray())

    private fun base64UrlEncode(bytes: ByteArray): String {
        // BigInteger.toByteArray()は符号ビットを含むことがあるので、先頭の0x00を除去
        val trimmedBytes =
            if (bytes.isNotEmpty() && bytes[0] == 0.toByte()) {
                bytes.copyOfRange(1, bytes.size)
            } else {
                bytes
            }

        return Base64
            .getUrlEncoder()
            .withoutPadding()
            .encodeToString(trimmedBytes)
    }

    operator fun invoke() = value

    override fun toString() = value.toString()

    companion object {
        fun load(text: String): PublicKey {
            val base64 =
                text
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replace("\\s".toRegex(), "")

            val keyBytes = Base64.getDecoder().decode(base64)
            val keySpec = X509EncodedKeySpec(keyBytes)
            val keyFactory = KeyFactory.getInstance("RSA")

            val publicKey = keyFactory.generatePublic(keySpec) as RSAPublicKey

            return PublicKey(publicKey)
        }
    }
}
