package net.brightroom.migration._configuration

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.v1.core.DatabaseConfig
import org.jetbrains.exposed.v1.jdbc.Database
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MigrateConfiguration(
    private val migrationProperties: MigrationProperties,
) {
    @Bean
    fun database(): Database =
        Database.connect(
            url = migrationProperties.datasource.url,
            driver = migrationProperties.datasource.driverClassName,
            user = migrationProperties.datasource.username,
            password = migrationProperties.datasource.password,
            databaseConfig =
                DatabaseConfig {
                    useNestedTransactions = true
                },
        )

    @Bean
    fun flyway(): Flyway =
        Flyway
            .configure()
            .dataSource(
                migrationProperties.datasource.url,
                migrationProperties.datasource.username,
                migrationProperties.datasource.password,
            ).locations("classpath:migration")
            .baselineOnMigrate(true)
            .load()

    @Bean
    fun migratePackage(): String = migrationProperties.migratePackage

    @Bean
    fun outputDirectory(): String = migrationProperties.generateScript.outputDirectory

    @Bean
    fun filenameSuffix(): String = migrationProperties.generateScript.filenameSuffix
}
