package net.brightroom.jwks.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Jwks(
    @SerialName("keys") val keys: List<Jwk>,
)
