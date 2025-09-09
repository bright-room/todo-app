package net.brightroom.migration.generator.domain.model

/**
 *
 */
class MigrationPackage(
    private val value: String,
) {
    operator fun invoke(): String = value

    override fun toString(): String = value
}
