package net.brightroom.todo.domain.model.task.definition.content

import kotlin.jvm.JvmInline

/**
 * タスクのタイトル
 */
@JvmInline
value class Title(private val value: String) {
    init {
        require(value.isNotBlank()) { "Title cannot be blank" }
        require(value.length <= 100) { "Title cannot be more than 100 characters" }
    }

    operator fun invoke(): String = value

    override fun toString(): String = value
}
