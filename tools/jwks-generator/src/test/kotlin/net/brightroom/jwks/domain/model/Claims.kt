package net.brightroom.jwks.domain.model

@JvmInline
value class Claims(private val value: Map<String, String>) {
    operator fun invoke() = value

    override fun toString() = value.toString()

    companion object Companion {
        fun create(): Claims =
            Claims(
                mapOf(
                    "name" to "John Doe",
                    "role" to "admin",
                    "email" to "john.doe@example.com",
                ),
            )
    }
}
