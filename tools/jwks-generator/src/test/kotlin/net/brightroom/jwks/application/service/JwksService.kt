package net.brightroom.jwks.application.service

import net.brightroom.jwks.application.repository.JwksRepository
import net.brightroom.jwks.domain.model.Jwks
import org.springframework.stereotype.Service

@Service
class JwksService(
    private val jwksRepository: JwksRepository,
) {
    fun load(): Jwks = jwksRepository.load()
}
