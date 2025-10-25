package net.brightroom.jwks.application.service

import net.brightroom.jwks.application.repository.CreateJwksRepository
import net.brightroom.jwks.domain.model.KeyPair
import org.springframework.stereotype.Service

@Service
class CreateJwksService(private val createJwksRepository: CreateJwksRepository) {
    fun create(keyPair: KeyPair) = createJwksRepository.create(keyPair)
}
