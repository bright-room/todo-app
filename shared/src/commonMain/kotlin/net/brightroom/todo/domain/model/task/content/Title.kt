package net.brightroom.todo.domain.model.task.content

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * タスクのタイトル
 */
@JvmInline
@Serializable
value class Title(
    private val value: String,
) {
    init {
        require(value.isNotBlank()) { "Title cannot be blank" }
    }

    operator fun invoke(): String = value

    override fun toString(): String = value
}
