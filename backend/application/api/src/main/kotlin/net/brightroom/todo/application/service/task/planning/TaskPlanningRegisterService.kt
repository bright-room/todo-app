package net.brightroom.todo.application.service.task.planning

import net.brightroom.todo.application.repository.task.planning.TaskPlanningRegisterRepository

class TaskPlanningRegisterService(
    private val taskPlanningRegisterRepository: TaskPlanningRegisterRepository,
) : TaskPlanningRegisterRepository by taskPlanningRegisterRepository
