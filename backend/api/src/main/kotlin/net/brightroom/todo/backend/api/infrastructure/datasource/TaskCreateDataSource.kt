package net.brightroom.todo.backend.api.infrastructure.datasource

import net.brightroom.todo.backend.api.application.repository.TaskCreateRepository
import net.brightroom.todo.shared.domain.model.Task

/**
 * タスク作成データソース実装
 */
class TaskCreateDataSource(
    private val taskDataSource: TaskDataSource,
) : TaskCreateRepository {
    override suspend fun create(task: Task): Task = taskDataSource.addTask(task)
}
