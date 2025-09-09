package net.brightroom.migration.detector.domain.model.table

import org.jetbrains.exposed.v1.core.Table

data class MigrationTables(
    val name: List<Table>,
) {
    fun toArray() = name.toTypedArray()

    override fun toString() = name.joinToString(",") { it.tableName }
}
