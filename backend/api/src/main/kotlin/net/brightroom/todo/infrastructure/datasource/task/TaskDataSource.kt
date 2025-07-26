package net.brightroom.todo.infrastructure.datasource.task

import net.brightroom.todo._extensions.exposed.transaction
import net.brightroom.todo.application.repository.task.TaskRepository
import net.brightroom.todo.domain.model.task.Task
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.Tasks
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase

class TaskDataSource(
    private val db: R2dbcDatabase,
) : TaskRepository {
    override suspend fun get(id: TaskId): Task =
        transaction(db, readOnly = true) {
            TaskMapper.get(id)
        }

    override suspend fun listAll(): Tasks =
        transaction(db, readOnly = true) {
            TaskMapper.listAll()
        }
}
