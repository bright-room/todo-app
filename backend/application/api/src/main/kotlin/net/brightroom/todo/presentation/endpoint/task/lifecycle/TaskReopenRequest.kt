package net.brightroom.todo.presentation.endpoint.task.lifecycle

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.identity.Id

@Serializable
data class TaskReopenRequest(val id: Id)
