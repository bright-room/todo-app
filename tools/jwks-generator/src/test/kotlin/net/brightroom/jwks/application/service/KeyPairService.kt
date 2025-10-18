package net.brightroom.jwks.application.service

import net.brightroom.jwks.application.repository.KeyPairRepository
import net.brightroom.jwks.domain.model.KeyPair
import org.springframework.stereotype.Service

@Service
class KeyPairService(private val keyPairRepository: KeyPairRepository) {
    fun load(): KeyPair = keyPairRepository.load()
}
