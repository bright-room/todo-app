package net.brightroom.todo.domain.model.task.subtask

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.task.Task
import net.brightroom.todo.domain.model.task.duedate.TaskDueDateThreshold

/**
 * サブタスク一覧
 */
@Serializable
data class SubTasks(val list: List<Subtask>)
