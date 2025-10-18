package net.brightroom.todo.presentation.endpoint.task.planning

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.Planning

@Serializable
data class TaskPlanningRegisterRequest(val id: Id, val planning: Planning = Planning())
