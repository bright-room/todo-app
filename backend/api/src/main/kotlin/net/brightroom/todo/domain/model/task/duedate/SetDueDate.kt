package net.brightroom.todo.domain.model.task.duedate

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * タスクの期限日
 */
@JvmInline
@Serializable
value class SetDueDate(private val value: LocalDate) : DueDate {
    override fun is期限日セット済み(): Boolean = true

    override operator fun invoke(): LocalDate = value
    override fun toString(): String = value.toString()
}
