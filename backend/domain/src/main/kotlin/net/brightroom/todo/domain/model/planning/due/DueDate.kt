package net.brightroom.todo.domain.model.planning.due

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.policy.serializer.DueDateSerializer

/**
 * 期限日を表すインターフェース
 */
@Serializable(with = DueDateSerializer::class)
interface DueDate {
    fun is期限日がセット済み(): Boolean

    operator fun invoke(): LocalDate
}
