package net.brightroom.jwks.application.repository

import net.brightroom.jwks.domain.model.Jwks
import net.brightroom.jwks.domain.model.Jwt
import net.brightroom.jwks.domain.model.KeyPair

interface CreateJwtRepository {
    fun create(
        keyPair: KeyPair,
        jwks: Jwks,
    ): Jwt
}
