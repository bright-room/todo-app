package net.brightroom.todo.domain.model.task.duedate

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import net.brightroom.todo._extensions.kotlinx.datetime.now
import kotlin.jvm.JvmInline

/**
 * タスクの期日
 */
@JvmInline
@Serializable
value class SetDueDate(private val value: LocalDate) : DueDate {
    init {
        val now = LocalDate.now()
        require(value >= now) { "Due date must be in the future" }
    }

    override fun invoke(): LocalDate = value
    override fun is期日がセットされている(): Boolean = true

    override fun toString(): String = value.toString()
}