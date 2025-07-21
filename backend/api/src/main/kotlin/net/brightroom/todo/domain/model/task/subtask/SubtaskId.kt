package net.brightroom.todo.domain.model.task.subtask

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * サブタスクID
 */
@JvmInline
@Serializable
@OptIn(ExperimentalUuidApi::class)
value class SubtaskId(private val value: Uuid) {
    operator fun invoke(): Uuid = value
    override fun toString(): String = value.toString()

    companion object {
        fun create(): SubtaskId = SubtaskId(Uuid.random())
    }
}