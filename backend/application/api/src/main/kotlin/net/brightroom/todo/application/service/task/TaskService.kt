package net.brightroom.todo.application.service.task

import net.brightroom.todo.application.repository.task.TaskRepository

class TaskService(
    private val taskRepository: TaskRepository,
) : TaskRepository by taskRepository
