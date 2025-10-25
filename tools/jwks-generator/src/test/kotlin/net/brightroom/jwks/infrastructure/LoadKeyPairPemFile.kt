package net.brightroom.jwks.infrastructure

import net.brightroom.jwks.application.repository.KeyPairRepository
import net.brightroom.jwks.domain.model.KeyPair
import net.brightroom.jwks.domain.model.PrivateKey
import net.brightroom.jwks.domain.model.PublicKey
import org.springframework.stereotype.Repository
import java.nio.file.Path

@Repository
class LoadKeyPairPemFile(private val saveDir: Path) : KeyPairRepository {
    override fun load(): KeyPair {
        val privateKey = loadPrivateKeyFile()
        val publicKey = loadPublicKeyFile()

        return KeyPair(publicKey, privateKey)
    }

    private fun loadPrivateKeyFile(): PrivateKey {
        val path = saveDir.resolve("private_key.pem")
        val file = path.toFile()

        val privateKey = file.readText()
        return PrivateKey.load(privateKey)
    }

    private fun loadPublicKeyFile(): PublicKey {
        val path = saveDir.resolve("public_key.pem")
        val file = path.toFile()

        val publicKey = file.readText()
        return PublicKey.load(publicKey)
    }
}
