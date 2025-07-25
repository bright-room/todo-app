package net.brightroom.todo.domain.model.task.duedate

import kotlinx.datetime.LocalDate


class NoSetDueDate : DueDate {
    override operator fun invoke(): LocalDate = throw UnsupportedOperationException("No Set Due Date")
    override fun is期日がセットされている(): Boolean = false

    override fun toString(): String = ""
}