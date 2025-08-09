package net.brightroom.migration._configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("migration")
data class MigrationProperties
    @ConstructorBinding
    constructor(
        val datasource: DataSourceProperties,
        val migratePackage: String,
        val generateScript: GenerateScriptProperties,
    )

data class DataSourceProperties(
    val url: String,
    val username: String,
    val password: String,
    val driverClassName: String,
)

data class GenerateScriptProperties(
    val outputDirectory: String,
    val filenameSuffix: String,
)
