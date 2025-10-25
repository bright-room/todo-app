package net.brightroom.todo.presentation.endpoint.task.planning

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.Priority

@Serializable
data class TaskPriorityRegisterRequest(val id: Id, val priority: Priority)
