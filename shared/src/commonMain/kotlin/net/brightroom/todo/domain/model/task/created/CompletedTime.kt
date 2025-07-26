package net.brightroom.todo.domain.model.task.created

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/**
 * タスク完了日時
 */
@Serializable
sealed interface CompletedTime {
    operator fun invoke(): LocalDateTime

    companion object {
        fun now(): CompletedTime = SetCompletedTime()

        fun create(value: LocalDateTime?): CompletedTime = value?.let(::SetCompletedTime) ?: NoSetCompletedTime()
    }
}
