package net.brightroom.todo.domain.model

import kotlinx.serialization.Serializable

/**
 * タスク一覧
 */
@JvmInline
@Serializable
value class Tasks(
    val list: List<Task>,
)
