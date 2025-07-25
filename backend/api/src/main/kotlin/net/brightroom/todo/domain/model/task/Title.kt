package net.brightroom.todo.domain.model.task

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * タスクのタイトル
 */
@JvmInline
@Serializable
value class Title(private val value: String) {
    init {
        require(value.isBlank()) { "Title cannot be blank" }
        require(value.length <= 100) { "Title cannot be more than 100 characters" }
    }

    operator fun invoke(): String = value
    override fun toString(): String = value
}