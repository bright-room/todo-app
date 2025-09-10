package net.brightroom.migration.detector.schema.dummy

import org.jetbrains.exposed.v1.core.Table

object NonMigratableTable : Table("non_migratable_table") {
    val id = integer("id")
}
