package net.brightroom.todo.domain.model.task.complete

import kotlinx.datetime.LocalDateTime
import kotlin.jvm.JvmInline

/** 完了日時 */
@JvmInline
value class SetCompletedTime(
    private val value: LocalDateTime,
) : CompletedTime {
    override operator fun invoke() = value

    override fun toString() = value.toString()
}
