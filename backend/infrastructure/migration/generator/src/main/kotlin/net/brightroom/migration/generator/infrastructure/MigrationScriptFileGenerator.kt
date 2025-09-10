package net.brightroom.migration.generator.infrastructure

import net.brightroom.migration.detector.domain.model.table.MigrationTables
import net.brightroom.migration.generator.application.repository.CreateMigrationScriptRepository
import net.brightroom.migration.generator.domain.model.OutputDirectory
import net.brightroom.migration.generator.domain.model.ScriptName
import org.jetbrains.exposed.v1.core.ExperimentalDatabaseMigrationApi
import org.jetbrains.exposed.v1.migration.MigrationUtils
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class MigrationScriptFileGenerator(
    private val outputDirectory: OutputDirectory,
    private val scriptName: ScriptName,
) : CreateMigrationScriptRepository {
    @OptIn(ExperimentalDatabaseMigrationApi::class)
    @Transactional(readOnly = true)
    override fun create(tables: MigrationTables) {
        MigrationUtils.generateMigrationScript(
            *tables.toArray(),
            scriptDirectory = outputDirectory(),
            scriptName = scriptName(),
            withLogs = false,
        )
    }
}
