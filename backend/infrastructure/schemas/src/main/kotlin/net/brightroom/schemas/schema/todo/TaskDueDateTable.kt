@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.annotation.Migratable
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

@Migratable
object TaskDueDateTable : IntIdTable("task_due_date") {
    val task_id = reference("task_id", TaskIdTable)
    val due_date = datetime("due_date")
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
