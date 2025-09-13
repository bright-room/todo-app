package net.brightroom.todo.domain.model.planning.due

import kotlinx.datetime.LocalDate

/**
 * 期限日生成
 */
object DueDateFactory {
    fun create(value: LocalDate?): DueDate {
        if (value == null) return NoSetDueDate()
        return SetDueDate(value)
    }
}
