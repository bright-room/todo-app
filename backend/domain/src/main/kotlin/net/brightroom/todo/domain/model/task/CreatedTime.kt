package net.brightroom.todo.domain.model.task

import kotlinx.datetime.LocalDateTime
import net.brightroom._extensions.kotlinx.datetime.now
import kotlin.jvm.JvmInline

/** 作成日時 */
@JvmInline
value class CreatedTime(
    private val value: LocalDateTime,
) {
    operator fun invoke() = value

    override fun toString() = value.toString()

    companion object {
        fun now(): CreatedTime = CreatedTime(LocalDateTime.now())
    }
}
