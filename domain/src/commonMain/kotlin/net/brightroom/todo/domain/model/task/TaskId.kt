@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.domain.model.task

import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * タスクID
 */
@JvmInline
value class TaskId(private val value: Uuid) {
    operator fun invoke(): Uuid = value

    override fun toString(): String = value.toString()
}
