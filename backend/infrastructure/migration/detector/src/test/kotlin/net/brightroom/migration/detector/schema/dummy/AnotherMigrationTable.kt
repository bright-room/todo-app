package net.brightroom.migration.detector.schema.dummy

import net.brightroom.migration.detector.Migratable
import org.jetbrains.exposed.v1.core.Table

@Migratable
object AnotherMigrationTable : Table("another_migration_table") {
    val id = integer("id")
    val description = text("description")
}
