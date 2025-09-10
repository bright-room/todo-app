package net.brightroom.migration.detector.schema.dummy

import net.brightroom.migration.detector.Migratable
import org.jetbrains.exposed.v1.core.Table

@Migratable
object TestMigrationTable : Table("test_migration_table") {
    val id = integer("id")
    val name = varchar("name", 255)
}
