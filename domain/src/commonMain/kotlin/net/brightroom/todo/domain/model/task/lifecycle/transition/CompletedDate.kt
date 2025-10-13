package net.brightroom.todo.domain.model.task.lifecycle.transition

import kotlinx.datetime.LocalDate
import kotlin.jvm.JvmInline

/**
 * タスクの完了日
 */
@JvmInline
value class CompletedDate(private val value: LocalDate) {
    operator fun invoke(): LocalDate = value

    override fun toString(): String = value.toString()
}
