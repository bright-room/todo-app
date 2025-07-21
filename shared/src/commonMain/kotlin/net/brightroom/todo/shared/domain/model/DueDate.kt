package net.brightroom.todo.shared.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * タスクの期日
 */
@JvmInline
@Serializable
value class DueDate(
    private val value: LocalDate,
) {
    operator fun invoke(): LocalDate = value

    override fun toString(): String = value.toString()

    fun toLocalDate(): LocalDate = value

    companion object {
        fun of(
            year: Int,
            month: Int,
            dayOfMonth: Int,
        ): DueDate = DueDate(LocalDate(year, month, dayOfMonth))

        fun parse(dateString: String): DueDate = DueDate(LocalDate.parse(dateString))
    }
}
