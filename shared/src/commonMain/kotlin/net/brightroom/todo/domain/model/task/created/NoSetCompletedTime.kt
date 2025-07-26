package net.brightroom.todo.domain.model.task.created

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * 完了日時(未セット状態)
 */
@JvmInline
@Serializable
value class NoSetCompletedTime private constructor(
    private val value: LocalDateTime? = null,
) : CompletedTime {
    constructor() : this(null)

    override operator fun invoke(): LocalDateTime = throw IllegalStateException("CompletedTime is not set")

    override fun toString(): String = ""
}
