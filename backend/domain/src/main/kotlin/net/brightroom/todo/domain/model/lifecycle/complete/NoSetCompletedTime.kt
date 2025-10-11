package net.brightroom.todo.domain.model.lifecycle.complete

import kotlinx.datetime.LocalDateTime

/**
 * 未完了状態の完了日時
 */
class NoSetCompletedTime : CompletedTime {
    override fun is完了日時が設定されている(): Boolean = false

    override operator fun invoke(): LocalDateTime = throw IllegalStateException("CompletedTime is not set")

    override fun toString(): String = ""
}
