package net.brightroom.migration.detector.domain.model.table.dummy

import net.brightroom.migration.annotation.Migratable
import org.jetbrains.exposed.v1.core.Table

@Migratable
object TestTable2 : Table("test_table_2") {
    val id = integer("id")
    val description = text("description")
}
