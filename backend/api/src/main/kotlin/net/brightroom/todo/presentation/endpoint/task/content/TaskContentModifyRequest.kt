package net.brightroom.todo.presentation.endpoint.task.content

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.content.Content

@Serializable
data class TaskContentModifyRequest(
    val id: TaskId,
    val content: Content,
)
