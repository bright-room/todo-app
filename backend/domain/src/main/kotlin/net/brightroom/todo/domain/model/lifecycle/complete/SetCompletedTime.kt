package net.brightroom.todo.domain.model.lifecycle.complete

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/**
 * 設定済みの完了日時
 */
@JvmInline
@Serializable
value class SetCompletedTime(
    private val value: LocalDateTime,
) : CompletedTime {
    override operator fun invoke(): LocalDateTime = value

    override fun toString(): String = value.toString()
}
