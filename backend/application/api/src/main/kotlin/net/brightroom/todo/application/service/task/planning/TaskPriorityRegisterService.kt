package net.brightroom.todo.application.service.task.planning

import net.brightroom.todo.application.repository.task.planning.TaskPriorityRegisterRepository

class TaskPriorityRegisterService(
    private val taskPriorityRegisterRepository: TaskPriorityRegisterRepository,
) : TaskPriorityRegisterRepository by taskPriorityRegisterRepository
