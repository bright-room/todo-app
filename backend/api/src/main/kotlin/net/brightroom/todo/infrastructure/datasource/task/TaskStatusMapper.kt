package net.brightroom.todo.infrastructure.datasource.task

import net.brightroom.todo.domain.model.task.Status
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import net.brightroom.todo.infrastructure.datasource.exposed.TaskStatusTable
import org.jetbrains.exposed.v1.r2dbc.insert
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
object TaskStatusMapper {
    suspend fun register(status: Status, id: TaskId, createdTime: CreatedTime) {
        TaskStatusTable.insert {
            it[taskId] = id()
            it[this.status] = status
            it[createdAt] = createdTime()
        }
    }
}
