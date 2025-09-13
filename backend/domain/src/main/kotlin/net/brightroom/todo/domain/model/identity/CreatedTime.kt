package net.brightroom.todo.domain.model.identity

import kotlinx.datetime.LocalDateTime

/**
 * 作成日時
 */
@JvmInline
value class CreatedTime(
    private val value: LocalDateTime,
) {
    operator fun invoke(): LocalDateTime = value

    override fun toString(): String = value.toString()
}
