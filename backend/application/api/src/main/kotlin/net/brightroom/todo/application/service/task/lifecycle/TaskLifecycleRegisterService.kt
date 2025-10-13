package net.brightroom.todo.application.service.task.lifecycle

import net.brightroom.todo.application.repository.task.lifecycle.TaskLifecycleRegisterRepository

class TaskLifecycleRegisterService(private val taskLifecycleRegisterRepository: TaskLifecycleRegisterRepository) :
    TaskLifecycleRegisterRepository by taskLifecycleRegisterRepository
