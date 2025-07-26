package net.brightroom.todo.application.repository.task

import net.brightroom.todo.domain.model.task.TaskId

interface TaskCompleteRepository {
    suspend fun complete(id: TaskId)

    suspend fun revertCompletion(id: TaskId)
}
