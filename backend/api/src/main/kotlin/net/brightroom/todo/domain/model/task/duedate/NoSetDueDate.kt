package net.brightroom.todo.domain.model.task.duedate

import kotlinx.datetime.LocalDate

/**
 * タスクの期限日
 */
object NoSetDueDate : DueDate {
    override fun is期限日セット済み(): Boolean = false

    override operator fun invoke(): LocalDate = throw UnsupportedOperationException("No due date set")
    override fun toString(): String = ""
}