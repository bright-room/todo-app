package net.brightroom.todo.domain.model.task.created

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import net.brightroom.todo._extensions.kotlinx.datetime.now
import kotlin.jvm.JvmInline

/**
 * 完了日時(セット済み)
 */
@JvmInline
@Serializable
value class SetCompletedTime(
    private val value: LocalDateTime,
) : CompletedTime {
    constructor() : this(LocalDateTime.now())

    override operator fun invoke(): LocalDateTime = value

    override fun toString(): String = value.toString()
}
