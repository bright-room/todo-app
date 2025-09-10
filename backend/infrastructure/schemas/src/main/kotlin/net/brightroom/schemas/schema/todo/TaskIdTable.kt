@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.annotation.Migratable
import net.brightroom.schemas.schema.extensions.uuid.KotlinUUIDTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

@Migratable
object TaskIdTable : KotlinUUIDTable("task_id") {
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
