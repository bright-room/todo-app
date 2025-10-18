package net.brightroom.todo.domain.model.task.planning.schedule.due

import kotlinx.datetime.LocalDate
import kotlin.jvm.JvmInline

/**
 * タスクの予定期限日
 */
@JvmInline
value class DueDate(private val value: LocalDate) {
    fun is予定期限日が予定開始日よりも前(beginDate: BeginDate) = value > beginDate()

    operator fun invoke(): LocalDate = value

    override fun toString(): String = value.toString()
}
