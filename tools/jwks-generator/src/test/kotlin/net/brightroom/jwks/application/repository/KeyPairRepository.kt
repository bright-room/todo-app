package net.brightroom.jwks.application.repository

import net.brightroom.jwks.domain.model.KeyPair

interface KeyPairRepository {
    fun load(): KeyPair
}
