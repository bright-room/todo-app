package net.brightroom.todo.domain.model.lifecycle

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.lifecycle.complete.CompletedTime
import net.brightroom.todo.domain.model.lifecycle.complete.CompletedTimeFactory

/**
 * ライフサイクル
 */
@Serializable
data class Lifecycle(
    val status: Status = Status.未完了,
    val completedTime: CompletedTime = CompletedTimeFactory.create(null),
)
