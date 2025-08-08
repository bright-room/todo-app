package net.brightroom.schemas.database.jdbc

data class DataSourceProperties(
    val jdbcUrl: String,
    val username: String,
    val password: String,
)
