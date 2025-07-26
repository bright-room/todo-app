package net.brightroom.todo.infrastructure.datasource.task

import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.created.CompletedTime
import net.brightroom.todo.infrastructure.datasource.exposed.TaskCompleteTimeTable
import org.jetbrains.exposed.v1.r2dbc.insert
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
object TaskCompleteTimeMapper {
    suspend fun register(
        completedTime: CompletedTime,
        id: TaskId,
    ) {
        TaskCompleteTimeTable.insert {
            it[taskId] = id()
            it[completedAt] = completedTime()
        }
    }
}
