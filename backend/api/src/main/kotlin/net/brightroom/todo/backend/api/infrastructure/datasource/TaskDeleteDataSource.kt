package net.brightroom.todo.backend.api.infrastructure.datasource

import net.brightroom.todo.backend.api.application.repository.TaskDeleteRepository
import net.brightroom.todo.shared.domain.model.TaskId

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
