package net.brightroom.todo.application.repository.task.content

import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.content.Content

interface TaskContentRegisterRepository {
    suspend fun register(
        id: TaskId,
        content: Content,
    )
}
