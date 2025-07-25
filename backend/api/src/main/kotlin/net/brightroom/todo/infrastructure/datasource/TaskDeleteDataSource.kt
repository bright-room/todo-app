package net.brightroom.todo.infrastructure.datasource

import net.brightroom.todo.backend.api.application.repository.TaskDeleteRepository
import net.brightroom.todo.domain.model.task.TaskId

/**
 * タスク削除データソース実装
 */
class TaskDeleteDataSource(
    private val taskDataSource: TaskDataSource,
) : TaskDeleteRepository {
    override suspend fun delete(taskId: TaskId) {
        taskDataSource.removeTask(taskId)
    }
}
