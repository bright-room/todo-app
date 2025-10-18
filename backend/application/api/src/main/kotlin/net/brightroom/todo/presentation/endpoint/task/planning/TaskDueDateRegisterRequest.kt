package net.brightroom.todo.presentation.endpoint.task.planning

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.due.DueDate

@Serializable
data class TaskDueDateRegisterRequest(val id: Id, val dueDate: DueDate)
