package net.brightroom.todo.domain.model.task.planning.schedule.reminder

import kotlinx.datetime.LocalTime
import kotlin.jvm.JvmInline

/**
 * リマインド基準時刻
 */
@JvmInline
value class TimeOfDay(private val value: LocalTime) {
    operator fun invoke(): LocalTime = value

    override fun toString(): String = value.toString()
}
