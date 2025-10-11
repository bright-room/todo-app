package net.brightroom.todo.application.repository.task.planning

import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.due.DueDate

interface TaskDueDateRegisterRepository {
    suspend fun register(
        dueDate: DueDate,
        id: Id,
    )

    suspend fun clear(id: Id)
}
