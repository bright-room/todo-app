package net.brightroom.todo.application.service.task

import net.brightroom.todo.application.repository.task.TaskCompleteRepository

class TaskCompleteService(
    private val taskCompleteRepository: TaskCompleteRepository,
) : TaskCompleteRepository by taskCompleteRepository
