package net.brightroom.todo.application.repository.task

import net.brightroom.todo.domain.model.task.TaskId

interface TaskCreateRepository {
    suspend fun create(): TaskId
}
