package net.brightroom.todo.application.service.task.planning

import net.brightroom.todo.application.repository.task.planning.TaskDueDateRegisterRepository

class TaskDueDateRegisterService(
    private val taskDueDateRegisterRepository: TaskDueDateRegisterRepository,
) : TaskDueDateRegisterRepository by taskDueDateRegisterRepository
