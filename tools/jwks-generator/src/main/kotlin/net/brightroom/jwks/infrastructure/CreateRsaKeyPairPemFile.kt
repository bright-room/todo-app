package net.brightroom.jwks.infrastructure

import net.brightroom.jwks.application.repository.CreateKeyPairRepository
import net.brightroom.jwks.domain.model.KeyPair
import net.brightroom.jwks.domain.model.PrivateKey
import net.brightroom.jwks.domain.model.PublicKey
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.nio.file.Path

@Repository
class CreateRsaKeyPairPemFile(private val saveDir: Path) : CreateKeyPairRepository {
    private val log = LoggerFactory.getLogger(CreateRsaKeyPairPemFile::class.java)

    override fun create(): KeyPair {
        log.info("RSA鍵ペアを生成します")

        try {
            val keyPair = KeyPair.create()
            saveAsPrivateKey(keyPair.privateKey)
            saveAsPublicKey(keyPair.publicKey)

            return keyPair
        } catch (e: Exception) {
            throw RuntimeException("RSA鍵の生成に失敗", e)
        }
    }

    private fun saveAsPrivateKey(privateKey: PrivateKey) {
        val privateKeyPath = saveDir.resolve("private_key.pem")
        val privateKeyPemFile = privateKeyPath.toFile()
        privateKeyPemFile.createNewFile()

        privateKeyPemFile.writeText(privateKey.asPEM())

        log.info("✓ 秘密鍵を private_key.pem に保存しました")
    }

    private fun saveAsPublicKey(publicKey: PublicKey) {
        val publicKeyPath = saveDir.resolve("public_key.pem")
        val publicKeyPemFile = publicKeyPath.toFile()
        publicKeyPemFile.createNewFile()

        publicKeyPemFile.writeText(publicKey.asPEM())

        log.info("✓ 公開鍵を public_key.pem に保存しました")
    }
}
