package net.brightroom.migration.detector

import net.brightroom.migration.detector.domain.model.table.MigrationTables
import net.brightroom.migration.detector.schema.dummy.AnotherMigrationTable
import net.brightroom.migration.detector.schema.dummy.TestMigrationTable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    properties = ["migration.scan-base-package=net.brightroom.migration.detector.schema.dummy"],
)
@DisplayName("マイグレーション検出テスト")
class MigrationDetectorTest
    @Autowired
    constructor(private val migrationDetector: MigrationDetector) {
        @Test
        fun `マイグレーション対象テーブルが正しく検出される`() {
            val actual = migrationDetector.detect()
            val expected =
                MigrationTables(
                    listOf(
                        AnotherMigrationTable,
                        TestMigrationTable,
                    ),
                )

            assertEquals(expected, actual)
        }
    }
