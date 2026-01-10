package net.brightroom.todo.domain.model.task.planning

import net.brightroom.todo.domain.model.task.planning.schedule.Schedule

/**
 * タスクの計画
 */
data class Planning(private val priority: Priority, private val schedule: Schedule)
