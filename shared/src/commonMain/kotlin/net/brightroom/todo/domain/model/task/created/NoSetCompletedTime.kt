package net.brightroom.todo.domain.model.task.created

import kotlinx.datetime.LocalDateTime

/**
 * 完了日時(未セット状態)
 */
class NoSetCompletedTime : CompletedTime {
    override operator fun invoke(): LocalDateTime = throw IllegalStateException("CompletedTime is not set")

    override fun toString(): String = ""
}
