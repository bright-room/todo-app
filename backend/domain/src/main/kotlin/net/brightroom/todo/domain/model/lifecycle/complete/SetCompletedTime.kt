package net.brightroom.todo.domain.model.lifecycle.complete

import kotlinx.datetime.LocalDateTime

/**
 * 設定済みの完了日時
 */
@JvmInline
value class SetCompletedTime(
    private val value: LocalDateTime,
) : CompletedTime {
    override operator fun invoke(): LocalDateTime = value

    override fun toString(): String = value.toString()
}
