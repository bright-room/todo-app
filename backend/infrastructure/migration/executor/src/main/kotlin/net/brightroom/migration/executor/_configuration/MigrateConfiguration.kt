package net.brightroom.migration.executor._configuration

import net.brightroom.migration.executor.domain.model.MigrationPackage
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
class MigrateConfiguration(
    private val migrationProperties: MigrationProperties,
) {
    @Bean
    fun flyway(dataSource: DataSource): Flyway =
        Flyway
            .configure()
            .dataSource(dataSource)
            .locations("classpath:migration")
            .baselineOnMigrate(true)
            .load()

    @Bean
    fun migratePackage(): MigrationPackage {
        val migratePackage = migrationProperties.migratePackage
        return MigrationPackage(migratePackage)
    }
}
