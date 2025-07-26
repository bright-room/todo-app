package net.brightroom.todo.application.repository.task

import net.brightroom.todo.domain.model.task.Task
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.Tasks

interface TaskRepository {
    suspend fun get(id: TaskId): Task

    suspend fun listAll(): Tasks
}
