package net.brightroom.todo.application.service.task

import net.brightroom.todo.application.repository.task.CreateTaskRepository

class CreateTaskService(private val createTaskRepository: CreateTaskRepository) : CreateTaskRepository by createTaskRepository
