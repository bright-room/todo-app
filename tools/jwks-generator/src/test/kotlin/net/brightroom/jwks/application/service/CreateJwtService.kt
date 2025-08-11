package net.brightroom.jwks.application.service

import net.brightroom.jwks.application.repository.CreateJwtRepository
import net.brightroom.jwks.domain.model.Jwks
import net.brightroom.jwks.domain.model.Jwt
import net.brightroom.jwks.domain.model.KeyPair
import org.springframework.stereotype.Service

@Service
class CreateJwtService(
    private val createJwtRepository: CreateJwtRepository,
) {
    fun create(
        keyPair: KeyPair,
        jwks: Jwks,
    ): Jwt = createJwtRepository.create(keyPair, jwks)
}
