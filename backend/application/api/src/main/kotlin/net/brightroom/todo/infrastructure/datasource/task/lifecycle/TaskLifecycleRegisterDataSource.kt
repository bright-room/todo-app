@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.infrastructure.datasource.task.lifecycle

import net.brightroom.schemas.schema.todo.TaskCompleteTimeTable
import net.brightroom.schemas.schema.todo.TaskStatusTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.application.repository.task.lifecycle.TaskLifecycleRegisterRepository
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.lifecycle.Lifecycle
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insert
import kotlin.uuid.ExperimentalUuidApi

class TaskLifecycleRegisterDataSource(
    private val db: R2dbcDatabase,
) : TaskLifecycleRegisterRepository {
    override suspend fun register(
        lifecycle: Lifecycle,
        id: Id,
    ): Unit =
        transaction(db) {
            val createdTime = CreatedTime.now()

            TaskStatusTable.insert {
                it[TaskStatusTable.task_id] = id()
                it[TaskStatusTable.status] = lifecycle.status
                it[TaskStatusTable.created_at] = createdTime()
            }

            val status = lifecycle.status
            if (status.is完了済み()) {
                TaskCompleteTimeTable.insert {
                    it[TaskCompleteTimeTable.task_id] = id()
                    it[TaskCompleteTimeTable.completed_at] = createdTime()
                }
            }
        }
}
