@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.domain.model.label

import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * ラベルID
 */
@JvmInline
value class LabelId(private val value: Uuid) {
    operator fun invoke(): Uuid = value

    override fun toString(): String = value.toString()
}
