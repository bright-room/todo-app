package net.brightroom.migration.generator.application.service

import net.brightroom.migration.generator.application.repository.CreateMigrationScriptRepository
import org.jetbrains.exposed.v1.core.Table
import org.springframework.stereotype.Service

@Service
class CreateMigrationScriptService(
    private val createMigrationScriptRepository: CreateMigrationScriptRepository,
) {
    fun create(tables: Array<Table>) = createMigrationScriptRepository.create(tables)
}
