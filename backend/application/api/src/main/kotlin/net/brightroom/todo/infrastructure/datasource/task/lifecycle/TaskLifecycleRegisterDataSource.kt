@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.infrastructure.datasource.task.lifecycle

import kotlinx.datetime.LocalDateTime
import net.brightroom._extensions.kotlinx.datetime.now
import net.brightroom.schemas.schema.todo.TaskCompleteTimeTable
import net.brightroom.schemas.schema.todo.TaskReopenTimeTable
import net.brightroom.schemas.schema.todo.TaskStatusTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.application.repository.task.lifecycle.TaskLifecycleRegisterRepository
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.lifecycle.Status
import net.brightroom.todo.domain.model.lifecycle.complete.CompletedTimeFactory
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insert
import kotlin.uuid.ExperimentalUuidApi

class TaskLifecycleRegisterDataSource(private val db: R2dbcDatabase) : TaskLifecycleRegisterRepository {
    override suspend fun complete(id: Id): Unit =
        transaction(db) {
            val createdTime = CreatedTime.now()

            TaskStatusTable.insert {
                it[TaskStatusTable.task_id] = id()
                it[TaskStatusTable.status] = Status.完了
                it[TaskStatusTable.created_at] = createdTime()
            }

            val completedTime = CompletedTimeFactory.create(LocalDateTime.now())
            TaskCompleteTimeTable.insert {
                it[TaskCompleteTimeTable.task_id] = id()
                it[TaskCompleteTimeTable.completed_at] = completedTime()
            }
        }

    override suspend fun reopen(id: Id): Unit =
        transaction(db) {
            val createdTime = CreatedTime.now()

            TaskStatusTable.insert {
                it[TaskStatusTable.task_id] = id()
                it[TaskStatusTable.status] = Status.未完了
                it[TaskStatusTable.created_at] = createdTime()
            }

            val reopenedTime = ReopenTime.now()
            TaskReopenTimeTable.insert {
                it[TaskReopenTimeTable.task_id] = id()
                it[TaskReopenTimeTable.reopened_at] = reopenedTime()
            }
        }

    override suspend fun open(id: Id): Unit =
        transaction(db) {
            val createdTime = CreatedTime.now()
            TaskStatusTable.insert {
                it[TaskStatusTable.task_id] = id()
                it[TaskStatusTable.status] = Status.未完了
                it[TaskStatusTable.created_at] = createdTime()
            }
        }
}
