package net.brightroom.todo.domain.model.task.content

import kotlin.jvm.JvmInline

/** タスクのタイトル */
@JvmInline
value class Title(
    private val value: String,
) {
    operator fun invoke() = value

    override fun toString() = value
}
