package net.brightroom.todo.backend.api.infrastructure.datasource

import net.brightroom.todo.backend.api.application.repository.TaskRepository
import net.brightroom.todo.shared.domain.model.Task
import net.brightroom.todo.shared.domain.model.TaskId
import net.brightroom.todo.shared.domain.model.Tasks
import net.brightroom.todo.shared.domain.problem.TaskNotFoundException

/**
 * タスクデータソース実装
 */
class TaskDataSource : TaskRepository {
    // In-memory storage for demonstration purposes
    // This will be replaced with actual database operations when Exposed is properly configured
    private val taskStorage = mutableMapOf<TaskId, Task>()

    override suspend fun find(taskId: TaskId): Task =
        taskStorage[taskId]
            ?: throw TaskNotFoundException("Task not found: ${taskId.invoke()}")

    override suspend fun listAll(): Tasks = Tasks(taskStorage.values.toList())

    override suspend fun findCompleted(): Tasks {
        val completedTasks = taskStorage.values.filter { it.isCompleted }
        return Tasks(completedTasks)
    }

    override suspend fun findIncomplete(): Tasks {
        val incompleteTasks = taskStorage.values.filter { !it.isCompleted }
        return Tasks(incompleteTasks)
    }

    override suspend fun findOverdue(): Tasks {
        val overdueTasks = taskStorage.values.filter { it.isOverdue() }
        return Tasks(overdueTasks)
    }

    /**
     * Internal method to add task to storage (for other DataSource classes)
     */
    internal suspend fun addTask(task: Task): Task {
        taskStorage[task.id] = task
        return task
    }

    /**
     * Internal method to update task in storage (for other DataSource classes)
     */
    internal suspend fun updateTask(task: Task): Task {
        if (!taskStorage.containsKey(task.id)) {
            throw TaskNotFoundException("Task not found for update: ${task.id.invoke()}")
        }
        taskStorage[task.id] = task
        return task
    }

    /**
     * Internal method to remove task from storage (for other DataSource classes)
     */
    internal suspend fun removeTask(taskId: TaskId) {
        if (!taskStorage.containsKey(taskId)) {
            throw TaskNotFoundException("Task not found for deletion: ${taskId.invoke()}")
        }
        taskStorage.remove(taskId)
    }
}
