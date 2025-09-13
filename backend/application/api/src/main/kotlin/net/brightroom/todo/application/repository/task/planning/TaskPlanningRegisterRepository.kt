package net.brightroom.todo.application.repository.task.planning

import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.Planning

interface TaskPlanningRegisterRepository {
    suspend fun register(
        planning: Planning,
        id: Id,
    )
}
