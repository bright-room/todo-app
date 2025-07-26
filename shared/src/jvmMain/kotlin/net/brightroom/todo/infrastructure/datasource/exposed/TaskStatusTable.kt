package net.brightroom.todo.infrastructure.datasource.exposed

import net.brightroom.todo.domain.model.task.Status
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

object TaskStatusTable : IntIdTable("task_status") {
    @OptIn(ExperimentalUuidApi::class)
    val taskId = reference("task_id", TaskIdTable)
    val status =
        customEnumeration(
            name = "status",
            sql = "task_status_type",
            fromDb = { value -> Status.valueOf(value as String) },
            toDb = { value -> value },
        )
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
