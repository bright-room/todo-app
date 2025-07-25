package net.brightroom.todo.domain.model.task.duedate

import kotlinx.datetime.LocalDate

interface DueDate {
    operator fun invoke(): LocalDate
    fun is期日がセットされている(): Boolean
}