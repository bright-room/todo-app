package net.brightroom.migration.executor._configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("migration.execute")
data class MigrationExecutorProperties
    @ConstructorBinding
    constructor(val scriptLocation: String)
