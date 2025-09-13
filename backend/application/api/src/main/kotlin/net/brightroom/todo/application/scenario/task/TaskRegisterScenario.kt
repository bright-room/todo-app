package net.brightroom.todo.application.scenario.task

import net.brightroom.todo.application.service.task.CreateTaskRegisterService
import net.brightroom.todo.application.service.task.classification.TaskClassificationRegisterService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService
import net.brightroom.todo.application.service.task.lifecycle.TaskLifecycleRegisterService
import net.brightroom.todo.application.service.task.planning.TaskPlanningRegisterService
import net.brightroom.todo.domain.model.classification.Classification
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.lifecycle.Lifecycle
import net.brightroom.todo.domain.model.planning.Planning

class TaskRegisterScenario(
    private val createTaskRegisterService: CreateTaskRegisterService,
    private val taskContentRegisterService: TaskContentRegisterService,
    private val taskPlanningRegisterService: TaskPlanningRegisterService,
    private val taskLifecycleRegisterService: TaskLifecycleRegisterService,
    private val taskClassificationRegisterService: TaskClassificationRegisterService,
) {
    suspend fun register(
        content: Content,
        planning: Planning,
        lifecycle: Lifecycle,
        classification: Classification,
    ): Id {
        val id = createTaskRegisterService.create()

        taskContentRegisterService.register(content, id)
        taskPlanningRegisterService.register(planning, id)
        taskLifecycleRegisterService.register(lifecycle, id)
        taskClassificationRegisterService.register(classification, id)

        return id
    }
}
