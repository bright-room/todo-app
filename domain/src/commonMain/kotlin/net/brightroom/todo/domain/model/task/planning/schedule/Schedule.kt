package net.brightroom.todo.domain.model.task.planning.schedule

import net.brightroom.todo.domain.model.task.planning.schedule.due.DueDate
import net.brightroom.todo.domain.model.task.planning.schedule.reminder.Reminder

/**
 * タスクのスケジュール
 */
data class Schedule(private val dueDate: DueDate, private val reminder: Reminder)
