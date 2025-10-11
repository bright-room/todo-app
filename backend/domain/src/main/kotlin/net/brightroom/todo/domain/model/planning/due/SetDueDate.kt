package net.brightroom.todo.domain.model.planning.due

import kotlinx.datetime.LocalDate

/**
 * 設定済みの期限日
 */
@JvmInline
value class SetDueDate(
    private val value: LocalDate,
) : DueDate {
    override fun is期限日がセット済み(): Boolean = true

    override operator fun invoke(): LocalDate = value

    override fun toString(): String = value.toString()
}
