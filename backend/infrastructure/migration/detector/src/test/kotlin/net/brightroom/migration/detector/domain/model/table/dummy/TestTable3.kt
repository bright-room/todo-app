package net.brightroom.migration.detector.domain.model.table.dummy

import org.jetbrains.exposed.v1.core.Table

object TestTable3 : Table("test_table_3") {
    val id = integer("id")
    val value = decimal("value", 10, 2)
}
