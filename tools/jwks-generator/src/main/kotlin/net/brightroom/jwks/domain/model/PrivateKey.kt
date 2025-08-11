package net.brightroom.jwks.domain.model

import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import kotlin.jvm.JvmInline

/** 秘密鍵 */
@JvmInline
value class PrivateKey(
    private val value: RSAPrivateKey,
) {
    fun asPEM(): String {
        val encoded = value.encoded
        val base64 = Base64.getEncoder().encodeToString(encoded)
        return buildString {
            appendLine("-----BEGIN PRIVATE KEY-----")
            base64.chunked(64).forEach { appendLine(it) }
            appendLine("-----END PRIVATE KEY-----")
        }
    }

    operator fun invoke() = value

    override fun toString() = value.toString()

    companion object {
        fun load(text: String): PrivateKey {
            val base64 =
                text
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("\\s".toRegex(), "")

            val keyBytes = Base64.getDecoder().decode(base64)
            val keySpec = PKCS8EncodedKeySpec(keyBytes)
            val keyFactory = KeyFactory.getInstance("RSA")

            val privateKey = keyFactory.generatePrivate(keySpec) as RSAPrivateKey

            return PrivateKey(privateKey)
        }
    }
}
