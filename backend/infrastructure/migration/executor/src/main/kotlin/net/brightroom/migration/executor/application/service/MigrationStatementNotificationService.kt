package net.brightroom.migration.executor.application.service

import net.brightroom.migration.detector.domain.model.table.MigrationTables
import net.brightroom.migration.executor.application.repository.MigrationStatementNotificationRepository
import org.springframework.stereotype.Service

@Service
class MigrationStatementNotificationService(
    private val migrationStatementNotificationRepository: MigrationStatementNotificationRepository,
) {
    fun notify(tables: MigrationTables) = migrationStatementNotificationRepository.notify(tables)
}
