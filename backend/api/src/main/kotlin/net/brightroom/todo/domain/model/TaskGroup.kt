package net.brightroom.todo.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class TaskGroupId(val value: String) {
    companion object {
        fun generate(): TaskGroupId = TaskGroupId(UUID.randomUUID().toString())
        fun from(value: String): TaskGroupId = TaskGroupId(value)
    }
}

@OptIn(ExperimentalTime::class)
@Serializable
data class TaskGroup(
    val id: TaskGroupId,
    val title: String,
    @Contextual val createdAt: Instant,
    val tasks: List<Task> = emptyList()
) {
    init {
        require(title.isNotBlank()) { "TaskGroup title cannot be blank" }
        require(title.length <= 100) { "TaskGroup title cannot exceed 100 characters" }
    }

    fun updateTitle(newTitle: String): TaskGroup {
        require(newTitle.isNotBlank()) { "TaskGroup title cannot be blank" }
        require(newTitle.length <= 100) { "TaskGroup title cannot exceed 100 characters" }
        return copy(title = newTitle)
    }

    fun addTask(task: Task): TaskGroup {
        if (tasks.any { it.id == task.id }) return this
        return copy(tasks = tasks + task)
    }

    fun removeTask(taskId: TaskId): TaskGroup {
        return copy(tasks = tasks.filterNot { it.id == taskId })
    }

    fun updateTask(updatedTask: Task): TaskGroup {
        return copy(tasks = tasks.map { if (it.id == updatedTask.id) updatedTask else it })
    }
}