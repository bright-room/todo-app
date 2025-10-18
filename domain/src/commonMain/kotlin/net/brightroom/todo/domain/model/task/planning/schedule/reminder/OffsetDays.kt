package net.brightroom.todo.domain.model.task.planning.schedule.reminder

import kotlin.jvm.JvmInline

/**
 * リマインド基準日
 */
@JvmInline
value class OffsetDays(private val value: Int) {
    init {
        require(value > 0) { "OffsetDays must be greater than 0" }
    }

    operator fun invoke(): Int = value

    override fun toString(): String = value.toString()
}
