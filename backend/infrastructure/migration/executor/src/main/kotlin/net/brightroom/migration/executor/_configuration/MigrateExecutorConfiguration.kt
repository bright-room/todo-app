package net.brightroom.migration.executor._configuration

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.v1.spring.boot.autoconfigure.ExposedAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@ImportAutoConfiguration(
    value = [ExposedAutoConfiguration::class],
    exclude = [DataSourceTransactionManagerAutoConfiguration::class],
)
class MigrateExecutorConfiguration(private val migrationExecutorProperties: MigrationExecutorProperties) {
    @Bean
    fun flyway(dataSource: DataSource): Flyway =
        Flyway
            .configure()
            .dataSource(dataSource)
            .locations(migrationExecutorProperties.scriptLocation)
            .baselineOnMigrate(true)
            .load()
}
