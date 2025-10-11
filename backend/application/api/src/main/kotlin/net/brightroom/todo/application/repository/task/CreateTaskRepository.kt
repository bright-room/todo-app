package net.brightroom.todo.application.repository.task

import net.brightroom.todo.domain.model.identity.Id

interface CreateTaskRepository {
    suspend fun create(): Id
}
