package net.brightroom.todo.domain.model.task

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * タスクの説明
 */
@JvmInline
@Serializable
value class Description(private val value: String) {
    constructor(): this("")

    operator fun invoke(): String = value
    override fun toString(): String = value
}