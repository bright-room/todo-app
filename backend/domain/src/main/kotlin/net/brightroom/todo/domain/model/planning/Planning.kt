package net.brightroom.todo.domain.model.planning

import net.brightroom.todo.domain.model.planning.due.DueDate

/**
 * 計画
 */
data class Planning(
    val dueDate: DueDate,
    val priority: Priority,
)
