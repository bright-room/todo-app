package net.brightroom.todo.application.repository.task.lifecycle

import net.brightroom.todo.domain.model.identity.Id

interface TaskLifecycleRegisterRepository {
    suspend fun complete(id: Id)

    suspend fun reopen(id: Id)

    suspend fun open(id: Id)
}
