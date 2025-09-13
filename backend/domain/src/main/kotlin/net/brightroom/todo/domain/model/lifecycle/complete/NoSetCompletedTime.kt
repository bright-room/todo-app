package net.brightroom.todo.domain.model.lifecycle.complete

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/**
 * 未完了状態の完了日時
 */
@Serializable
class NoSetCompletedTime(
    private val value: String = "",
) : CompletedTime {
    override operator fun invoke(): LocalDateTime = throw IllegalStateException("CompletedTime is not set")

    override fun toString(): String = value
}
