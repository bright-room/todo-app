package net.brightroom.todo.domain.model.task

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import net.brightroom.todo._extensions.kotlinx.datetime.now
import kotlin.jvm.JvmInline

/**
 * タスクの作成日時
 */
@JvmInline
@Serializable
value class CreatedTime(
    private val value: LocalDateTime,
) {
    constructor() : this(LocalDateTime.now())

    operator fun invoke(): LocalDateTime = value

    override fun toString(): String = value.toString()
}
