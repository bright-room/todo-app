package net.brightroom.todo.presentation.endpoint.task

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.identity.Id

@Serializable
data class TaskRegisterResponse(
    val id: Id,
)
