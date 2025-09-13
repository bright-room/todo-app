package net.brightroom.todo.domain.model.planning.due

import kotlinx.datetime.LocalDate

/**
 * 期限日を表すインターフェース
 */
interface DueDate {
    operator fun invoke(): LocalDate
}
