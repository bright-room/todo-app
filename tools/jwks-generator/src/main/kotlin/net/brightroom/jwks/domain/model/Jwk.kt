package net.brightroom.jwks.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Jwk(
    @SerialName("kty") val keyType: String,
    @SerialName("e") val exponent: String,
    @SerialName("kid") val keyId: String,
    @SerialName("n") val modulus: String,
)
