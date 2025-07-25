package net.brightroom.todo.domain.model.task.content

import kotlinx.serialization.Serializable

/**
 * タスク内容
 */
@Serializable
data class Content(
    private val title: Title,
    private val description: Description,
)
