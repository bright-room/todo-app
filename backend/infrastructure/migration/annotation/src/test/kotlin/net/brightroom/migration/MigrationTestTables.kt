package net.brightroom.migration

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

@Migratable(order = 1)
object MigrationTestTable1 : IntIdTable("migration_test_1")

@Migratable(order = 2)
object MigrationTestTable2 : IntIdTable("migration_test_2")

object MigrationTestTable3 : IntIdTable("migration_test_3")
