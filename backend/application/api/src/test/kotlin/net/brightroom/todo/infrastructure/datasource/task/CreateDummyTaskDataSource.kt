package net.brightroom.todo.infrastructure.datasource.task

import net.brightroom.schemas.schema.todo.TaskCompleteTimeTable
import net.brightroom.schemas.schema.todo.TaskContentTable
import net.brightroom.schemas.schema.todo.TaskDueDateTable
import net.brightroom.schemas.schema.todo.TaskIdTable
import net.brightroom.schemas.schema.todo.TaskPriorityTable
import net.brightroom.schemas.schema.todo.TaskStatusTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.domain.model.Task
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.identity.Identity
import net.brightroom.todo.domain.model.lifecycle.Status
import net.brightroom.todo.domain.model.lifecycle.complete.CompletedTime
import net.brightroom.todo.domain.model.planning.Priority
import net.brightroom.todo.domain.model.planning.due.DueDate
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insert
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class CreateDummyTaskDataSource(private val db: R2dbcDatabase) {
    suspend fun create(vararg tasks: Task) =
        transaction(db) {
            tasks.forEach { task ->
                val identity = task.identity
                val id = identity.id

                registerTaskId(identity)

                val content = task.content
                registerTaskContent(id, content)

                val planning = task.planning
                val priority = planning.priority

                registerTaskPriority(id, priority)

                val dueDate = planning.dueDate
                if (dueDate.is期限日がセット済み()) {
                    registerTaskDueDate(id, dueDate)
                }

                val lifecycle = task.lifecycle
                val status = lifecycle.status

                registerTaskStatus(id, status)

                val completedTime = lifecycle.completedTime
                if (status.is完了済み()) {
                    registerCompletedTime(id, completedTime)
                }
            }
        }

    private suspend fun registerTaskId(identity: Identity) {
        TaskIdTable.insert {
            it[TaskIdTable.id] = identity.id()
            it[TaskIdTable.created_at] = identity.createdTime()
        }
    }

    private suspend fun registerTaskContent(
        id: Id,
        content: Content,
    ) {
        TaskContentTable.insert {
            it[TaskContentTable.task_id] = id()
            it[TaskContentTable.title] = content.title()
            it[TaskContentTable.description] = content.description()
        }
    }

    private suspend fun registerTaskPriority(
        id: Id,
        priority: Priority,
    ) {
        TaskPriorityTable.insert {
            it[TaskPriorityTable.task_id] = id()
            it[TaskPriorityTable.priority] = priority
        }
    }

    private suspend fun registerTaskDueDate(
        id: Id,
        dueDate: DueDate,
    ) {
        TaskDueDateTable.insert {
            it[TaskDueDateTable.task_id] = id()
            it[TaskDueDateTable.due_date] = dueDate()
        }
    }

    private suspend fun registerTaskStatus(
        id: Id,
        status: Status,
    ) {
        TaskStatusTable.insert {
            it[TaskStatusTable.task_id] = id()
            it[TaskStatusTable.status] = status
        }
    }

    private suspend fun registerCompletedTime(
        id: Id,
        completedTime: CompletedTime,
    ) {
        TaskCompleteTimeTable.insert {
            it[TaskCompleteTimeTable.task_id] = id()
            it[TaskCompleteTimeTable.completed_at] = completedTime()
        }
    }
}
