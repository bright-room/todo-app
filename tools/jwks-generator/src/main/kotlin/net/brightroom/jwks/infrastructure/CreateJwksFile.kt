@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.jwks.infrastructure

import kotlinx.serialization.json.Json
import net.brightroom.jwks.application.repository.CreateJwksRepository
import net.brightroom.jwks.domain.model.Jwk
import net.brightroom.jwks.domain.model.Jwks
import net.brightroom.jwks.domain.model.KeyPair
import net.brightroom.jwks.domain.model.PublicKey
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.nio.file.Path
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Repository
class CreateJwksFile(
    private val saveDir: Path,
) : CreateJwksRepository {
    private val log = LoggerFactory.getLogger(CreateJwksFile::class.java)

    private val json =
        Json {
            prettyPrint = true
        }

    override fun create(keyPair: KeyPair) {
        try {
            val jwks = createJwks(keyPair.publicKey)

            val path = saveDir.resolve("jwks.json")
            val file = path.toFile()
            file.createNewFile()

            val json = json.encodeToString(jwks)
            file.writeText(json)

            log.info("✓ JWKS を jwks.json に保存しました")
        } catch (e: Exception) {
            throw RuntimeException("jwks.jsonの作成に失敗", e)
        }
    }

    private fun createJwks(publicKey: PublicKey): Jwks {
        val keyId = Uuid.random()

        val jwk =
            Jwk(
                keyType = "RSA",
                keyId = keyId.toString(),
                modulus = publicKey.asEncodedModulus(),
                exponent = publicKey.asEncodedExponent(),
            )

        return Jwks(keys = listOf(jwk))
    }
}
