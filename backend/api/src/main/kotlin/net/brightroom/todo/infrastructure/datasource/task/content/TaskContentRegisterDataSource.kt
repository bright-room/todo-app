package net.brightroom.todo.infrastructure.datasource.task.content

import net.brightroom.todo._extensions.exposed.transaction
import net.brightroom.todo.application.repository.task.content.TaskContentRegisterRepository
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.content.Content
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase

class TaskContentRegisterDataSource(
    private val db: R2dbcDatabase,
) : TaskContentRegisterRepository {
    override suspend fun register(
        id: TaskId,
        content: Content,
    ) = transaction(db, readOnly = false) {
        val createdTime = CreatedTime()
        TaskContentRegisterMapper.register(content, id, createdTime)
    }
}
