package net.brightroom.migration.generator._configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("migration.generate")
data class MigrationGeneratorProperties
    @ConstructorBinding
    constructor(
        val outputDirectory: String,
        val filenameSuffix: String,
    )
