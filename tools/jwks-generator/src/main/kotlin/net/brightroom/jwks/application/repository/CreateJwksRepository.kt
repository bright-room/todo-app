package net.brightroom.jwks.application.repository

import net.brightroom.jwks.domain.model.KeyPair

interface CreateJwksRepository {
    fun create(keyPair: KeyPair)
}
