package net.brightroom.todo.domain.model.task

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.task.duedate.TaskDueDateThreshold

/**
 * タスク一覧
 */
@Serializable
data class Tasks(val list: List<Task>) {

    fun listOfOverdueTasks(today: LocalDate): Tasks = Tasks(list.filter { it.is期限超過(today) })
    fun listOfDueTodayTasks(today: LocalDate): Tasks = Tasks(list.filter { it.is期限当日(today) })
    fun getDueSoonTasks(today: LocalDate, threshold: Long = TaskDueDateThreshold): Tasks =
        Tasks(list.filter { it.isDueSoon(today, threshold) })
}