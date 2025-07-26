package net.brightroom.todo.infrastructure.datasource.task

import net.brightroom.todo._extensions.exposed.transaction
import net.brightroom.todo.application.repository.task.TaskCompleteRepository
import net.brightroom.todo.domain.model.task.Status
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.created.CompletedTime
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase

class TaskCompleteDataSource(
    private val db: R2dbcDatabase,
) : TaskCompleteRepository {
    override suspend fun complete(id: TaskId) =
        transaction(db, readOnly = false) {
            val createdTime = CreatedTime()
            TaskStatusMapper.register(Status.完了, id, createdTime)

            val completedTime = CompletedTime.now()
            TaskCompleteTimeMapper.register(completedTime, id)
        }

    override suspend fun revertCompletion(id: TaskId) =
        transaction(db, readOnly = false) {
            val createdTime = CreatedTime()
            TaskStatusMapper.register(Status.未完了, id, createdTime)
        }
}
