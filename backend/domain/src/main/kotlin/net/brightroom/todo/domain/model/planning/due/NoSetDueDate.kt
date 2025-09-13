package net.brightroom.todo.domain.model.planning.due

import kotlinx.datetime.LocalDate

/**
 * 未設定の期限日
 */
class NoSetDueDate : DueDate {
    override fun invoke(): LocalDate = throw IllegalStateException("DueDate is not set")

    override fun toString(): String = ""
}
