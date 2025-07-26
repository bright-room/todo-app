package net.brightroom.todo.domain.model.task.created

import kotlinx.datetime.LocalDateTime

/**
 * タスク完了日時
 */
interface CompletedTime {
    operator fun invoke(): LocalDateTime

    companion object {
        fun now(): CompletedTime = SetCompletedTime()
        fun create(value: LocalDateTime?): CompletedTime = value?.let(::SetCompletedTime) ?: NoSetCompletedTime()
    }
}
