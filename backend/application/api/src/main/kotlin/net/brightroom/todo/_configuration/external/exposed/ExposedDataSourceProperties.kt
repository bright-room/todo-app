package net.brightroom.todo._configuration.external.exposed

import kotlinx.serialization.Serializable

@Serializable
data class ExposedDataSourceProperties(
    val host: String,
    val port: Int,
    val database: String,
    val username: String,
    val password: String,
    val schema: String,
    val minimumIdle: Int,
    val maximumPoolSize: Int,
    val connectionValidateQuery: String,
)
