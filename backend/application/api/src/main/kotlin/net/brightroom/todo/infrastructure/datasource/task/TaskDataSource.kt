@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.infrastructure.datasource.task

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import net.brightroom.schemas.schema.todo.ActiveTask
import net.brightroom.schemas.schema.todo.ActiveTaskDueDate
import net.brightroom.schemas.schema.todo.LatestTaskCompleteTime
import net.brightroom.schemas.schema.todo.LatestTaskContent
import net.brightroom.schemas.schema.todo.LatestTaskPriority
import net.brightroom.schemas.schema.todo.LatestTaskStatus
import net.brightroom.schemas.schema.todo.TaskCompleteTimeTable
import net.brightroom.schemas.schema.todo.TaskContentTable
import net.brightroom.schemas.schema.todo.TaskDueDateTable
import net.brightroom.schemas.schema.todo.TaskIdTable
import net.brightroom.schemas.schema.todo.TaskPriorityTable
import net.brightroom.schemas.schema.todo.TaskStatusTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.application.repository.task.TaskRepository
import net.brightroom.todo.domain.model.Task
import net.brightroom.todo.domain.model.Tasks
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.content.Description
import net.brightroom.todo.domain.model.content.Title
import net.brightroom.todo.domain.model.identity.CreatedTime
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.identity.Identity
import net.brightroom.todo.domain.model.lifecycle.Lifecycle
import net.brightroom.todo.domain.model.lifecycle.complete.CompletedTimeFactory
import net.brightroom.todo.domain.model.planning.Planning
import net.brightroom.todo.domain.model.planning.due.DueDateFactory
import net.brightroom.todo.domain.policy.exception.ResourceNotFoundException
import org.jetbrains.exposed.v1.core.JoinType
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.r2dbc.Query
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.select
import kotlin.uuid.ExperimentalUuidApi

class TaskDataSource(
    private val db: R2dbcDatabase,
) : TaskRepository {
    override suspend fun getBy(id: Id): Task =
        transaction(db, readOnly = true) {
            val resultRow =
                baseQuery()
                    .where { ActiveTask[TaskIdTable.id] eq id() }
                    .firstOrNull() ?: throw ResourceNotFoundException("タスクが存在しない id: $id")

            return@transaction toTaskModel(resultRow)
        }

    override suspend fun listAll(): Tasks =
        transaction(db) {
            val tasks =
                baseQuery()
                    .map { toTaskModel(it) }
                    .toList()

            return@transaction Tasks(tasks)
        }

    private fun baseQuery(): Query =
        ActiveTask
            .join(
                otherTable = LatestTaskContent,
                joinType = JoinType.INNER,
                onColumn = ActiveTask[TaskIdTable.id],
                otherColumn = LatestTaskContent[TaskContentTable.task_id],
            ).join(
                otherTable = LatestTaskStatus,
                joinType = JoinType.INNER,
                onColumn = ActiveTask[TaskIdTable.id],
                otherColumn = LatestTaskStatus[TaskStatusTable.task_id],
            ).join(
                otherTable = LatestTaskPriority,
                joinType = JoinType.INNER,
                onColumn = ActiveTask[TaskIdTable.id],
                otherColumn = LatestTaskPriority[TaskPriorityTable.task_id],
            ).join(
                otherTable = ActiveTaskDueDate,
                joinType = JoinType.LEFT,
                onColumn = ActiveTask[TaskIdTable.id],
                otherColumn = ActiveTaskDueDate[TaskDueDateTable.task_id],
            ).join(
                otherTable = LatestTaskCompleteTime,
                joinType = JoinType.LEFT,
                onColumn = ActiveTask[TaskIdTable.id],
                otherColumn = LatestTaskCompleteTime[TaskCompleteTimeTable.task_id],
            ).select(
                ActiveTask[TaskIdTable.id],
                ActiveTask[TaskIdTable.created_at],
                LatestTaskContent[TaskContentTable.title],
                LatestTaskContent[TaskContentTable.description],
                LatestTaskPriority[TaskPriorityTable.priority],
                LatestTaskStatus[TaskStatusTable.status],
                ActiveTaskDueDate[TaskDueDateTable.due_date], // nullable
                LatestTaskCompleteTime[TaskCompleteTimeTable.completed_at], // nullable
            )

    private fun toTaskModel(resultRow: ResultRow): Task {
        val id = Id(resultRow[ActiveTask[TaskIdTable.id]].value)
        val createdTime = CreatedTime(resultRow[ActiveTask[TaskIdTable.created_at]])
        val identity = Identity(id, createdTime)

        val title = Title(resultRow[LatestTaskContent[TaskContentTable.title]])
        val description = Description(resultRow[LatestTaskContent[TaskContentTable.description]])
        val content = Content(title, description)

        val dueDate = DueDateFactory.create(resultRow.getOrNull(ActiveTaskDueDate[TaskDueDateTable.due_date]))
        val priority = resultRow[LatestTaskPriority[TaskPriorityTable.priority]]
        val planning = Planning(dueDate, priority)

        val status = resultRow[LatestTaskStatus[TaskStatusTable.status]]
        val completedAt = CompletedTimeFactory.create(resultRow.getOrNull(LatestTaskCompleteTime[TaskCompleteTimeTable.completed_at]))
        val lifecycle = Lifecycle(status, completedAt)

        return Task(identity, content, planning, lifecycle)
    }
}
