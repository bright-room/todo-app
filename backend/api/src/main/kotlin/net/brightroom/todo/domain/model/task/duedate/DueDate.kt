package net.brightroom.todo.domain.model.task.duedate

import kotlinx.datetime.LocalDate

/**
 * タスクの期限日
 */
interface DueDate {
    fun is期限日セット済み(): Boolean
    operator fun invoke(): LocalDate
}