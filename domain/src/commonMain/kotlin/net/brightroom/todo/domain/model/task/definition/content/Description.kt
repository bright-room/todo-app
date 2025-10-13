package net.brightroom.todo.domain.model.task.definition.content

import kotlin.jvm.JvmInline

/**
 * タスクの説明
 */
@JvmInline
value class Description(private val value: String = "") {
    init {
        require(value.length <= 65_536) { "Description cannot be more than 65,536 characters" }
    }

    operator fun invoke(): String = value

    override fun toString(): String = value
}
