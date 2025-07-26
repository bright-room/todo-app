package net.brightroom.todo.infrastructure.datasource.task.content

import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.content.Content
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import net.brightroom.todo.infrastructure.datasource.exposed.TaskContentTable
import org.jetbrains.exposed.v1.r2dbc.insert
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
object TaskContentRegisterMapper {
    suspend fun register(
        content: Content,
        id: TaskId,
        createdTime: CreatedTime,
    ) {
        TaskContentTable.insert {
            it[taskId] = id()
            it[title] = content.title()
            it[description] = content.description()
            it[createdAt] = createdTime()
        }
    }
}
