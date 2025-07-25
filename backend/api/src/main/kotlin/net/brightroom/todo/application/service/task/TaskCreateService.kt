package net.brightroom.todo.application.service.task

import net.brightroom.todo.application.repository.task.TaskCreateRepository

class TaskCreateService(
    private val taskCreateRepository: TaskCreateRepository,
) : TaskCreateRepository by taskCreateRepository
