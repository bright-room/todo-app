package net.brightroom.migration.executor._configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("migration")
data class MigrationProperties
    @ConstructorBinding
    constructor(
        val migratePackage: String,
    )
