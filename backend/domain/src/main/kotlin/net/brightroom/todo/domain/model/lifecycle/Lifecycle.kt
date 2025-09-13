package net.brightroom.todo.domain.model.lifecycle

import net.brightroom.todo.domain.model.lifecycle.complete.CompletedTime

/**
 * ライフサイクル
 */
data class Lifecycle(
    val status: Status,
    val completedTime: CompletedTime,
)
