@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.domain.model.identity

import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * タスクを識別する一意な識別子
 */
@JvmInline
value class Id(
    private val value: Uuid,
) {
    operator fun invoke(): Uuid = value

    override fun toString(): String = value.toString()
}
