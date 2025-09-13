package net.brightroom.todo.domain.model.planning

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.planning.due.DueDate
import net.brightroom.todo.domain.model.planning.due.DueDateFactory

/**
 * 計画
 */
@Serializable
data class Planning(
    val dueDate: DueDate = DueDateFactory.create(null),
    val priority: Priority = Priority.Medium,
)
