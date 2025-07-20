package net.brightroom.todo.domain.model.task.subtask

import kotlinx.serialization.Serializable

/**
 * サブタスク集約
 */
@Serializable
data class Subtask(
    val id: SubtaskId,
    val title: Title,
    val description: Description = Description("")
)