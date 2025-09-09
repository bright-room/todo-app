package net.brightroom.migration.generator._configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("migration")
data class MigrationProperties
    @ConstructorBinding
    constructor(
        val migratePackage: String,
        val generateScript: GenerateScriptProperties,
    )

data class GenerateScriptProperties(
    val outputDirectory: String,
    val filenameSuffix: String,
)
