package net.brightroom.todo.application.repository.task.content

import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.identity.Id

interface TaskContentRegisterRepository {
    suspend fun register(
        content: Content,
        id: Id,
    )
}
