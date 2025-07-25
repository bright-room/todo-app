package net.brightroom.todo.infrastructure.datasource

import net.brightroom.todo.backend.api.application.repository.TaskCompleteRepository
import net.brightroom.todo.domain.model.task.TaskId

/**
 * タスク完了データソース実装
 */
class TaskCompleteDataSource(
    private val taskDataSource: TaskDataSource,
) : TaskCompleteRepository {
    override suspend fun complete(taskId: TaskId) {
        val task = taskDataSource.find(taskId)
        val completedTask = task.complete()
        taskDataSource.updateTask(completedTask)
    }

    override suspend fun uncomplete(taskId: TaskId) {
        val task = taskDataSource.find(taskId)
        val uncompletedTask = task.uncomplete()
        taskDataSource.updateTask(uncompletedTask)
    }
}
