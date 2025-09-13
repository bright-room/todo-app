package net.brightroom.todo.domain.model.planning.due

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * 未設定の期限日
 */
@Serializable
class NoSetDueDate(
    private val value: String = "",
) : DueDate {
    override fun is期限日がセット済み(): Boolean = false

    override fun invoke(): LocalDate = throw IllegalStateException("DueDate is not set")

    override fun toString(): String = value
}
