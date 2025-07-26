package net.brightroom.todo.infrastructure.datasource.exposed

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

object TaskCompleteTimeTable : IntIdTable("task_complete_time") {
    @OptIn(ExperimentalUuidApi::class)
    val taskId = reference("task_id", TaskIdTable)
    val completedAt = datetime("completed_at").defaultExpression(CurrentDateTime).index()
}
