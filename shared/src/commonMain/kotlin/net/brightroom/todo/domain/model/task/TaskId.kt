package net.brightroom.todo.domain.model.task

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * タスクを表す識別子
 */
@OptIn(ExperimentalUuidApi::class)
@JvmInline
@Serializable
value class TaskId(
    val value: Uuid,
) {
    constructor() : this(Uuid.random())
    constructor(value: String) : this(Uuid.parse(value))

    operator fun invoke(): Uuid = value

    override fun toString(): String = value.toString()
}
