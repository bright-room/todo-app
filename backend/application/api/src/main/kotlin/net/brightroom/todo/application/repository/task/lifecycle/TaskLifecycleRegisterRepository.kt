package net.brightroom.todo.application.repository.task.lifecycle

import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.lifecycle.Lifecycle

interface TaskLifecycleRegisterRepository {
    suspend fun register(
        lifecycle: Lifecycle,
        id: Id,
    )
}
