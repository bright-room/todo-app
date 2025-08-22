package net.brightroom.todo.domain.model.task.content

import kotlin.jvm.JvmInline

/** タスクの内容 */
@JvmInline
value class Description(
    private val value: String = "",
) {
    operator fun invoke() = value

    override fun toString() = value
}
