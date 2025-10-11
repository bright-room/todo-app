package net.brightroom.todo.application.scenario.task

import net.brightroom.todo.application.service.task.CreateTaskService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService
import net.brightroom.todo.application.service.task.lifecycle.TaskLifecycleRegisterService
import net.brightroom.todo.application.service.task.planning.TaskPriorityRegisterService
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.Priority

class CreateTaskScenario(
    private val createTaskService: CreateTaskService,
    private val taskContentRegisterService: TaskContentRegisterService,
    private val taskPriorityRegisterService: TaskPriorityRegisterService,
    private val taskLifecycleRegisterService: TaskLifecycleRegisterService,
) {
    suspend fun register(content: Content): Id {
        val id = createTaskService.create()

        taskContentRegisterService.register(content, id)
        taskPriorityRegisterService.register(Priority.Medium, id)
        taskLifecycleRegisterService.open(id)

        return id
    }
}
