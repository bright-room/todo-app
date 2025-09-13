package net.brightroom.todo.application.repository.task

import net.brightroom.todo.domain.model.Task
import net.brightroom.todo.domain.model.Tasks
import net.brightroom.todo.domain.model.identity.Id

interface TaskRepository {
    suspend fun getBy(id: Id): Task

    suspend fun listAll(): Tasks
}
