@file:OptIn(ExperimentalTime::class)

package net.brightroom.jwks.infrastructure

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import net.brightroom.jwks.application.repository.CreateJwtRepository
import net.brightroom.jwks.domain.model.Claims
import net.brightroom.jwks.domain.model.Jwks
import net.brightroom.jwks.domain.model.Jwt
import net.brightroom.jwks.domain.model.KeyPair
import org.springframework.stereotype.Repository
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant

@Repository
class CreateJwtToken : CreateJwtRepository {
    override fun create(
        keyPair: KeyPair,
        jwks: Jwks,
    ): Jwt {
        val now = Clock.System.now()
        val expiration = now.plus(1.hours)

        val claims = Claims.create()

        val builder = JWT.create()
        builder.withAudience("http://0.0.0.0:8080/hello")
        builder.withIssuer("http://0.0.0.0:8080/")
        builder.withExpiresAt(expiration.toJavaInstant())
        claims().forEach { builder.withClaim(it.key, it.value) }

        val token = builder.sign(Algorithm.RSA256(keyPair.publicKey(), keyPair.privateKey()))
        return Jwt(token)
    }
}
