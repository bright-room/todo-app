package net.brightroom.todo.presentation.endpoint.task.planning

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.identity.Id

@Serializable
data class TaskDueDateClearRequest(
    val id: Id,
)
