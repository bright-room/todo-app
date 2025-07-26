package net.brightroom.todo.infrastructure.datasource.task

import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import net.brightroom.todo.infrastructure.datasource.exposed.TaskIdTable
import org.jetbrains.exposed.v1.r2dbc.insertAndGetId
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
object TaskCreateMapper {
    suspend fun create(createdTime: CreatedTime): TaskId {
        val id = TaskIdTable.insertAndGetId { it[createdAt] = createdTime() }
        return TaskId(id.value)
    }
}
