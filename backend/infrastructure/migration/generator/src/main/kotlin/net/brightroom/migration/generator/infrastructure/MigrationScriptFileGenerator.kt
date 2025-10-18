package net.brightroom.migration.generator.infrastructure

import net.brightroom.migration.detector.domain.model.table.MigrationTables
import net.brightroom.migration.generator.application.repository.CreateMigrationScriptRepository
import net.brightroom.migration.generator.domain.model.OutputDirectory
import net.brightroom.migration.generator.domain.model.ScriptName
import org.jetbrains.exposed.v1.core.ExperimentalDatabaseMigrationApi
import org.jetbrains.exposed.v1.migration.MigrationUtils
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.io.File

@Repository
class MigrationScriptFileGenerator(private val outputDirectory: OutputDirectory, private val scriptName: ScriptName) :
    CreateMigrationScriptRepository {
    @OptIn(ExperimentalDatabaseMigrationApi::class)
    @Transactional(readOnly = true)
    override fun create(tables: MigrationTables) {
        MigrationUtils.generateMigrationScript(
            *tables.toArray(),
            scriptDirectory = outputDirectory(),
            scriptName = scriptName(),
            withLogs = false,
        )

        deleteIfEmptyFile()
    }

    private fun deleteIfEmptyFile() {
        val file = File(outputDirectory(), "${scriptName()}.sql")

        if (!file.exists()) return
        if (!file.isFile) return
        if (file.length() != 0L) return

        file.delete()
    }
}
