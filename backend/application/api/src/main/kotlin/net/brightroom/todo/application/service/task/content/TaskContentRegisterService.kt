package net.brightroom.todo.application.service.task.content

import net.brightroom.todo.application.repository.task.content.TaskContentRegisterRepository

class TaskContentRegisterService(private val taskContentRegisterRepository: TaskContentRegisterRepository) :
    TaskContentRegisterRepository by taskContentRegisterRepository
