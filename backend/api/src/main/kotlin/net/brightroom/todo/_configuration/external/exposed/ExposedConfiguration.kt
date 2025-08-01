package net.brightroom.todo._configuration.external.exposed

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopped
import io.ktor.server.plugins.di.annotations.Property
import io.ktor.server.plugins.di.dependencies
import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.codec.EnumCodec
import io.r2dbc.spi.IsolationLevel
import net.brightroom.todo.domain.model.task.Status
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabaseConfig
import org.jetbrains.exposed.v1.r2dbc.transactions.TransactionManager

fun Application.configure(
    @Property("external.datasource") properties: ExposedDataSourceProperties,
) {
    val options =
        PostgresqlConnectionConfiguration
            .builder()
            .host(properties.host)
            .port(properties.port)
            .database(properties.database)
            .schema(properties.schema)
            .username(properties.username)
            .password(properties.password)
            .codecRegistrar(EnumCodec.builder().withEnum("task_status_type", Status::class.java).build())
            .build()

    val poolConfiguration =
        ConnectionPoolConfiguration
            .builder(PostgresqlConnectionFactory(options))
            .minIdle(properties.minimumIdle)
            .maxSize(properties.maximumPoolSize)
            .validationQuery(properties.connectionValidateQuery)
            .build()

    val connectionPool = ConnectionPool(poolConfiguration)

    val db =
        R2dbcDatabase.connect(
            connectionFactory = connectionPool,
            databaseConfig =
                R2dbcDatabaseConfig {
                    explicitDialect = PostgreSQLDialect()
                    defaultR2dbcIsolationLevel = IsolationLevel.REPEATABLE_READ
                },
        )

    monitor.subscribe(ApplicationStopped) {
        TransactionManager.closeAndUnregister(db)
        connectionPool.dispose()
    }

    dependencies {
        provide<R2dbcDatabase> { db }
    }
}
