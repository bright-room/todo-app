package net.brightroom.todo.application.repository.task.planning

import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.Priority

interface TaskPriorityRegisterRepository {
    suspend fun register(
        priority: Priority,
        id: Id,
    )
}
