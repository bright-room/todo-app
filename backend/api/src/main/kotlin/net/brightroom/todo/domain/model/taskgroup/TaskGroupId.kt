package net.brightroom.todo.domain.model.taskgroup

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * タスクグループID
 */
@JvmInline
@Serializable
value class TaskGroupId(private val value: String) {
    operator fun invoke(): String = value
    override fun toString(): String = value

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun generate(): TaskGroupId = TaskGroupId(Uuid.random().toString())
    }
}