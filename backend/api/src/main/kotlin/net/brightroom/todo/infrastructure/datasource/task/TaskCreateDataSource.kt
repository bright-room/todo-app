package net.brightroom.todo.infrastructure.datasource.task

import net.brightroom.todo._extensions.exposed.transaction
import net.brightroom.todo.application.repository.task.TaskCreateRepository
import net.brightroom.todo.domain.model.task.Status
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase

class TaskCreateDataSource(
    private val db: R2dbcDatabase,
) : TaskCreateRepository {
    override suspend fun create(): TaskId =
        transaction(db, readOnly = false) {
            val cratedTime = CreatedTime()
            val id = TaskCreateMapper.create(cratedTime)
            TaskStatusMapper.register(Status.未完了, id, cratedTime)

            return@transaction id
        }
}
