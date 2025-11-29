package net.brightroom.todo.domain.model.task.planning.schedule.reminder

/**
 * タスクのリマインダー
 */
data class Reminder(private val offsetDays: OffsetDays, private val timeOfDay: TimeOfDay)
