package net.brightroom.jwks.application.repository

import net.brightroom.jwks.domain.model.Jwks

interface JwksRepository {
    fun load(): Jwks
}
