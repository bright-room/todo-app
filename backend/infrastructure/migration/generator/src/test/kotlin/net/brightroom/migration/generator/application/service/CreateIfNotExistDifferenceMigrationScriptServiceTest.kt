package net.brightroom.migration.generator.application.service

import net.brightroom.migration.detector.domain.model.table.MigrationTables
import net.brightroom.migration.generator.domain.model.OutputDirectory
import net.brightroom.migration.generator.domain.model.ScriptName
import net.brightroom.migration.generator.infrastructure.datasource.schema.TestTable1
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.io.File

@SpringBootTest(
    properties = [
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "migration.generate.output-directory=src/test/resources/migration-test",
        "migration.generate.filename-suffix=generate-test2",
    ],
)
class CreateIfNotExistDifferenceMigrationScriptServiceTest
    @Autowired
    constructor(
        private val outputDirectory: OutputDirectory,
        private val scriptName: ScriptName,
        private val createMigrationScriptService: CreateMigrationScriptService,
    ) {
        @Transactional
        @Test
        fun `スキーマ定義とデータベースの状態に差分がない場合マイグレーションファイルは生成されない`() {
            val tables = MigrationTables(listOf(TestTable1))
            SchemaUtils.create(*tables.toArray())

            createMigrationScriptService.create(tables)

            val actual = File(outputDirectory(), "${scriptName()}.sql")
            assertFalse(actual.exists())
        }
    }
