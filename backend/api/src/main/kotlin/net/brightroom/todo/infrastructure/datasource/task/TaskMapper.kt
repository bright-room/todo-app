package net.brightroom.todo.infrastructure.datasource.task

import io.ktor.http.invoke
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import net.brightroom.todo.domain.model.task.CreatedTime
import net.brightroom.todo.domain.model.task.Task
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.Tasks
import net.brightroom.todo.domain.model.task.content.Content
import net.brightroom.todo.domain.model.task.content.Description
import net.brightroom.todo.domain.model.task.content.Title
import net.brightroom.todo.domain.model.task.created.CompletedTime
import net.brightroom.todo.infrastructure.datasource.exposed.TaskCompleteTimeTable
import net.brightroom.todo.infrastructure.datasource.exposed.TaskContentTable
import net.brightroom.todo.infrastructure.datasource.exposed.TaskIdTable
import net.brightroom.todo.infrastructure.datasource.exposed.TaskStatusTable
import org.jetbrains.exposed.v1.core.JoinType
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.alias
import org.jetbrains.exposed.v1.r2dbc.select
import org.jetbrains.exposed.v1.r2dbc.selectAll
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
object TaskMapper {
    suspend fun get(id: TaskId): Task {
        val result =
            TaskIdTable
                .join(latestContent, JoinType.INNER, TaskIdTable.id, latestContent[TaskContentTable.taskId])
                .join(latestStatus, JoinType.INNER, TaskIdTable.id, latestStatus[TaskStatusTable.taskId])
                .join(latestCompleteTime, JoinType.LEFT, TaskIdTable.id, latestCompleteTime[TaskCompleteTimeTable.taskId])
                .select(
                    TaskIdTable.id,
                    TaskIdTable.createdAt,
                    latestContent[TaskContentTable.title],
                    latestContent[TaskContentTable.description],
                    latestStatus[TaskStatusTable.status],
                    latestCompleteTime[TaskCompleteTimeTable.completedAt],
                ).where { TaskIdTable.id eq id() }
                .firstOrNull()
                ?: throw IllegalArgumentException("Task not found: $id")

        return result.toTask()
    }

    suspend fun listAll(): Tasks {
        val result =
            TaskIdTable
                .join(latestContent, JoinType.INNER, TaskIdTable.id, latestContent[TaskContentTable.taskId])
                .join(latestStatus, JoinType.INNER, TaskIdTable.id, latestStatus[TaskStatusTable.taskId])
                .join(latestCompleteTime, JoinType.LEFT, TaskIdTable.id, latestCompleteTime[TaskCompleteTimeTable.taskId])
                .select(
                    TaskIdTable.id,
                    TaskIdTable.createdAt,
                    latestContent[TaskContentTable.title],
                    latestContent[TaskContentTable.description],
                    latestStatus[TaskStatusTable.status],
                    latestCompleteTime[TaskCompleteTimeTable.completedAt],
                ).map { it.toTask() }
                .toList()

        return Tasks(result)
    }

    private val latestContent =
        TaskContentTable
            .selectAll()
            .withDistinctOn(TaskContentTable.taskId)
            .orderBy(TaskContentTable.taskId to SortOrder.DESC, TaskContentTable.createdAt to SortOrder.DESC)
            .alias("latest_content")

    private val latestStatus =
        TaskStatusTable
            .selectAll()
            .withDistinctOn(TaskStatusTable.taskId)
            .orderBy(TaskStatusTable.taskId to SortOrder.DESC, TaskStatusTable.createdAt to SortOrder.DESC)
            .alias("latest_status")

    private val latestCompleteTime =
        TaskCompleteTimeTable
            .selectAll()
            .withDistinctOn(TaskCompleteTimeTable.taskId)
            .orderBy(TaskCompleteTimeTable.taskId to SortOrder.DESC, TaskCompleteTimeTable.completedAt to SortOrder.DESC)
            .alias("latest_complete_time")

    private fun ResultRow.toTask(): Task =
        Task(
            id = TaskId(this[TaskIdTable.id].value),
            content =
                Content(
                    title = Title(this[latestContent[TaskContentTable.title]]),
                    description = Description(this[latestContent[TaskContentTable.description]]),
                ),
            status = this[latestStatus[TaskStatusTable.status]],
            completedTime = CompletedTime.create(this.getOrNull(latestCompleteTime[TaskCompleteTimeTable.completedAt])),
            createdTime = CreatedTime(this[TaskIdTable.createdAt]),
        )
}
