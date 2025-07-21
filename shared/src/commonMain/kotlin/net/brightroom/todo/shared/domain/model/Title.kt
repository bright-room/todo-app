package net.brightroom.todo.shared.domain.model

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
    operator fun invoke(): String = value

    override fun toString(): String = value
}
