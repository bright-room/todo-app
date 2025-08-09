@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.Migratable
import net.brightroom.todo.domain.model.task.Status
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

@Migratable(order = 2)
object TaskStatusTable : IntIdTable("task_status") {
    val taskId = reference("task_id", TaskIdTable)
    val status = enumerationByName<Status>("status", 5)
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
