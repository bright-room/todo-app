package net.brightroom.todo.presentation.endpoint.task

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.task.TaskId

@Serializable
data class TaskCompleteRequest(
    val id: TaskId,
)
