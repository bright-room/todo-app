package net.brightroom.todo.application.service.task

import net.brightroom.todo.application.repository.task.CreateTaskRegisterRepository

class CreateTaskRegisterService(
    private val createTaskRegisterRepository: CreateTaskRegisterRepository,
) : CreateTaskRegisterRepository by createTaskRegisterRepository
