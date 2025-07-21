package net.brightroom.todo.backend.api.infrastructure.datasource

import net.brightroom.todo.backend.api.application.repository.TaskUpdateRepository
import net.brightroom.todo.shared.domain.model.Task

/**
 * タスク更新データソース実装
 */
class TaskUpdateDataSource(
    private val taskDataSource: TaskDataSource,
) : TaskUpdateRepository {
    override suspend fun update(task: Task): Task = taskDataSource.updateTask(task)
}
