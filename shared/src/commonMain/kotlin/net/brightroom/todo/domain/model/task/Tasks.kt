package net.brightroom.todo.domain.model.task

import kotlinx.serialization.Serializable

@Serializable
data class Tasks(
    val tasks: List<Task>,
)
