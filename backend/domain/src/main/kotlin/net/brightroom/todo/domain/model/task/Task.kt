package net.brightroom.todo.domain.model.task

import net.brightroom.todo.domain.model.task.complete.CompletedTime
import net.brightroom.todo.domain.model.task.content.Content
import net.brightroom.todo.domain.model.task.status.Status

/**
 * タスク
 */
data class Task(
    val taskId: TaskId,
    val content: Content,
    val status: Status,
    val createdTime: CreatedTime,
    val completedTime: CompletedTime,
)
