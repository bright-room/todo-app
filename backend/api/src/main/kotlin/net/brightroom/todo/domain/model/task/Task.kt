package net.brightroom.todo.domain.model.task

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.task.duedate.DueDate
import net.brightroom.todo.domain.model.task.duedate.NoSetDueDate

/**
 * タスク
 */
@Serializable
data class Task(
    val id: TaskId,
    val title: Title,
    val description: Description = Description(),
    val dueDate: DueDate = NoSetDueDate(),
    val state: State = State.未完了
)
