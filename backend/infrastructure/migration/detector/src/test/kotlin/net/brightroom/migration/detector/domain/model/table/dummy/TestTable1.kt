package net.brightroom.migration.detector.domain.model.table.dummy

import net.brightroom.migration.detector.Migratable
import org.jetbrains.exposed.v1.core.Table

@Migratable
object TestTable1 : Table("test_table_1") {
    val id = integer("id")
    val name = varchar("name", 50)
}
