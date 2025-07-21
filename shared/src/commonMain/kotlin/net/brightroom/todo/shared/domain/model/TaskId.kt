package net.brightroom.todo.shared.domain.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * タスクID
 */
@JvmInline
@Serializable
value class TaskId(
    private val value: String,
) {
    operator fun invoke(): String = value

    override fun toString(): String = value

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun generate(): TaskId = TaskId(Uuid.random().toString())

        fun of(value: String): TaskId = TaskId(value)
    }
}
