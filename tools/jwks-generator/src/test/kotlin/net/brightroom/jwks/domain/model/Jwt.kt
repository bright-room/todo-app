package net.brightroom.jwks.domain.model

import kotlin.jvm.JvmInline

@JvmInline
value class Jwt(
    private val value: String,
) {
    operator fun invoke() = value

    override fun toString() = value
}
