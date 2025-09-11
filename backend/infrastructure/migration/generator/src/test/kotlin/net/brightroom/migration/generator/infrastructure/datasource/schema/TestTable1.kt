package net.brightroom.migration.generator.infrastructure.datasource.schema

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object TestTable1 : IntIdTable("test_table_1") {
    val test_col = varchar("test_col", 255)
}
