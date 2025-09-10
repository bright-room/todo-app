package net.brightroom.migration.generator.application.service

import net.brightroom.migration.detector.domain.model.table.MigrationTables
import net.brightroom.migration.generator.application.repository.CreateMigrationScriptRepository
import org.springframework.stereotype.Service

@Service
class CreateMigrationScriptService(
    private val createMigrationScriptRepository: CreateMigrationScriptRepository,
) {
    fun create(tables: MigrationTables) = createMigrationScriptRepository.create(tables)
}
