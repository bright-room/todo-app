package net.brightroom.todo.infrastructure.datasource

import net.brightroom.todo.backend.api.application.repository.TaskUpdateRepository
import net.brightroom.todo.domain.model.task.Task

/**
 * タスク更新データソース実装
 */
class TaskUpdateDataSource(
    private val taskDataSource: TaskDataSource,
) : TaskUpdateRepository {
    override suspend fun update(task: Task): Task = taskDataSource.updateTask(task)
}
