package net.brightroom.jwks.application.service

import net.brightroom.jwks.application.repository.CreateKeyPairRepository
import org.springframework.stereotype.Service

@Service
class CreateKeyPairService(
    private val createKeyPairRepository: CreateKeyPairRepository,
) {
    fun create() = createKeyPairRepository.create()
}
