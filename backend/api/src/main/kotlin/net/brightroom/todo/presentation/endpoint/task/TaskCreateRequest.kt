package net.brightroom.todo.presentation.endpoint.task

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.task.content.Description
import net.brightroom.todo.domain.model.task.content.Title

@Serializable
data class TaskCreateRequest(
    val title: Title,
    val description: Description = Description(""),
)
