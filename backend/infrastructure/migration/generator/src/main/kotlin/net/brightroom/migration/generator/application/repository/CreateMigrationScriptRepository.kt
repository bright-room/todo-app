package net.brightroom.migration.generator.application.repository

import net.brightroom.migration.detector.domain.model.table.MigrationTables

interface CreateMigrationScriptRepository {
    fun create(tables: MigrationTables)
}
