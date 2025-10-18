@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.infrastructure.datasource.task.planning

import net.brightroom.schemas.schema.todo.InvalidTaskDueDateTable
import net.brightroom.schemas.schema.todo.TaskDueDateTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.application.repository.task.planning.TaskDueDateRegisterRepository
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.due.DueDate
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insert
import org.jetbrains.exposed.v1.r2dbc.select
import kotlin.uuid.ExperimentalUuidApi

class TaskDueDateRegisterDataSource(private val db: R2dbcDatabase) : TaskDueDateRegisterRepository {
    override suspend fun register(
        dueDate: DueDate,
        id: Id,
    ): Unit =
        transaction(db) {
            if (!dueDate.is期限日がセット済み()) throw IllegalArgumentException("DueDate is not set")

            val ceratedTime = CreatedTime.now()
            TaskDueDateTable.insert {
                it[TaskDueDateTable.task_id] = id()
                it[TaskDueDateTable.due_date] = dueDate()
                it[TaskDueDateTable.created_at] = ceratedTime()
            }
        }

    override suspend fun clear(id: Id): Unit =
        transaction(db) {
            InvalidTaskDueDateTable.insert(
                TaskDueDateTable
                    .select(TaskDueDateTable.id)
                    .where { TaskDueDateTable.task_id eq id() },
            )
        }
}
