package net.brightroom.todo.domain.model.task

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.task.content.Content
import net.brightroom.todo.domain.model.task.created.CompletedTime

/**
 * タスク
 */
@Serializable
data class Task(
    val id: TaskId,
    val content: Content,
    val status: Status,
    val completedTime: CompletedTime,
    val createdTime: CreatedTime,
)
