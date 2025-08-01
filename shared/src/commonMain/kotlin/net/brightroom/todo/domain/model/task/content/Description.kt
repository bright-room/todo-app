package net.brightroom.todo.domain.model.task.content

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * タスクの説明
 */
@JvmInline
@Serializable
value class Description(
    private val value: String,
) {
    operator fun invoke(): String = value

    override fun toString(): String = value
}
