package net.brightroom.schemas.schema.extensions.uuid

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.Table.Dual.clientDefault
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
open class KotlinUUIDTable(
    name: String = "",
    columnName: String = "id",
) : IdTable<Uuid>(name) {
    final override val id: Column<EntityID<Uuid>> = kotlinUUID(columnName).autoGenerate().entityId()
    final override val primaryKey = PrimaryKey(id)
}

@OptIn(ExperimentalUuidApi::class)
internal fun Table.kotlinUUID(name: String): Column<Uuid> = registerColumn(name, KotlinUUIDColumnType())

@OptIn(ExperimentalUuidApi::class)
internal fun Column<Uuid>.autoGenerate(): Column<Uuid> = clientDefault { Uuid.random() }
