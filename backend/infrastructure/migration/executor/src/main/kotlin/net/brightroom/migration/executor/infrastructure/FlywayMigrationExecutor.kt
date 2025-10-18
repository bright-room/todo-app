package net.brightroom.migration.executor.infrastructure

import net.brightroom.migration.executor.application.repository.MigrationExecuteRepository
import org.flywaydb.core.Flyway
import org.springframework.stereotype.Repository

@Repository
class FlywayMigrationExecutor(private val flyway: Flyway) : MigrationExecuteRepository {
    override fun execute() {
        flyway.migrate()
    }
}
