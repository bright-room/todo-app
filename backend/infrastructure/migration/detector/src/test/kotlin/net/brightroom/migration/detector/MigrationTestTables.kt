package net.brightroom.migration.detector

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

@Migratable
object MigrationTestTable1 : IntIdTable("migration_test_1")

@Migratable
object MigrationTestTable2 : IntIdTable("migration_test_2") {
    val migration_test_1_id = reference("migration_test_1_id", MigrationTestTable1)
}

object MigrationTestTable3 : IntIdTable("migration_test_3")
