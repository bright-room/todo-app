package net.brightroom.schemas.database.jdbc

import org.jetbrains.exposed.v1.core.DatabaseConfig
import org.jetbrains.exposed.v1.jdbc.Database

class DataSourceFactory(
    private val properties: DataSourceProperties,
) {
    fun create(): Database =
        Database.connect(
            driver = "org.postgresql.Driver",
            url = properties.jdbcUrl,
            user = properties.username,
            password = properties.password,
            databaseConfig =
                DatabaseConfig.invoke {
                    useNestedTransactions = true
                },
        )
}
