package net.brightroom.todo.domain.model.task

import kotlinx.datetime.LocalDateTime
import kotlin.jvm.JvmInline

/** 作成日時 */
@JvmInline
value class CreatedTime(
    private val value: LocalDateTime,
) {
    operator fun invoke() = value

    override fun toString() = value.toString()
}
