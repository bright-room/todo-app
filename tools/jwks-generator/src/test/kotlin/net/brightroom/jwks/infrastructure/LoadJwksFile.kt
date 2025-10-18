package net.brightroom.jwks.infrastructure

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.brightroom.jwks.application.repository.JwksRepository
import net.brightroom.jwks.domain.model.Jwks
import org.springframework.stereotype.Repository
import java.nio.file.Path

@Repository
class LoadJwksFile(private val saveDir: Path) : JwksRepository {
    private val jsonMapper = Json { prettyPrint = true }

    override fun load(): Jwks {
        val path = saveDir.resolve("jwks.json")
        val file = path.toFile()

        return jsonMapper.decodeFromString(file.readText())
    }
}
