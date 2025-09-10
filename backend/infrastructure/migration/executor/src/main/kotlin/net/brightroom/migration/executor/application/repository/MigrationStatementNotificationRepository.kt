package net.brightroom.migration.executor.application.repository

import net.brightroom.migration.detector.domain.model.table.MigrationTables

interface MigrationStatementNotificationRepository {
    fun notify(tables: MigrationTables)
}
