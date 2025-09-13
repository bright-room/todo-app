package net.brightroom.todo.domain.model.planning.due

import kotlinx.datetime.LocalDate

/**
 * 期限日を表すインターフェース
 */
interface DueDate {
    fun is期限日がセット済み(): Boolean

    operator fun invoke(): LocalDate
}
