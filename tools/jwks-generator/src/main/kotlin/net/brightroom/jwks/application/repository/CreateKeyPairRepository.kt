package net.brightroom.jwks.application.repository

import net.brightroom.jwks.domain.model.KeyPair

interface CreateKeyPairRepository {
    fun create(): KeyPair
}
