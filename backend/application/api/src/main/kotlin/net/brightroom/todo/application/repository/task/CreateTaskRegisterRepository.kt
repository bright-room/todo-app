package net.brightroom.todo.application.repository.task

import net.brightroom.todo.domain.model.identity.Id

interface CreateTaskRegisterRepository {
    suspend fun create(): Id
}
