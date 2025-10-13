package net.brightroom.todo.domain.model.task.planning.schedule.due

import kotlinx.datetime.LocalDate
import kotlin.jvm.JvmInline

/**
 * タスクの開始予定日
 */
@JvmInline
value class BeginDate(private val value: LocalDate) {
    operator fun invoke(): LocalDate = value

    override fun toString(): String = value.toString()
}
