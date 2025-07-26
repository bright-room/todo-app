package net.brightroom.todo._extensions.exposed

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.Table.Dual.clientDefault
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
open class UuidIdTable(
    name: String = "",
    columnName: String = "id",
) : IdTable<Uuid>(name) {
    final override val id: Column<EntityID<Uuid>> = kUuid(columnName).autoGenerate().entityId()
    final override val primaryKey = PrimaryKey(id)
}

@OptIn(ExperimentalUuidApi::class)
fun Table.kUuid(name: String): Column<Uuid> = registerColumn(name, UuidColumnType())

@OptIn(ExperimentalUuidApi::class)
fun Column<Uuid>.autoGenerate(): Column<Uuid> = clientDefault { Uuid.random() }
