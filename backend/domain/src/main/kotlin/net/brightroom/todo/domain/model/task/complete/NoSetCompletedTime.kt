package net.brightroom.todo.domain.model.task.complete

import kotlinx.datetime.LocalDateTime

/**
 * 完了日時
 */
class NoSetCompletedTime : CompletedTime {
    override operator fun invoke(): LocalDateTime = throw IllegalStateException("CompletedTime is not set")

    override fun toString(): String = ""
}
