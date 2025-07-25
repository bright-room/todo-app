package net.brightroom.todo.domain.model.task

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * タスクID
 */
@JvmInline
@Serializable
@OptIn(ExperimentalUuidApi::class)
value class TaskId(private val value: Uuid) {
    constructor(value: String): this(Uuid.parse(value))
    constructor(): this(Uuid.random())

    operator fun invoke(): Uuid = value
    override fun toString(): String = value.toString()
}
