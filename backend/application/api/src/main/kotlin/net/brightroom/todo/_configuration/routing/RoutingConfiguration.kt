@file:OptIn(ExperimentalSerializationApi::class)

package net.brightroom.todo._configuration.routing

import dev.hayden.KHealth
import io.ktor.serialization.kotlinx.json.jsonIo
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.di.annotations.Property
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.ExperimentalSerializationApi
import net.brightroom._extensions.kotlinx.serialization.CustomJson
import net.brightroom.todo._configuration.Environment
import net.brightroom.todo.application.scenario.task.CreateTaskScenario
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService
import net.brightroom.todo.application.service.task.lifecycle.TaskLifecycleRegisterService
import net.brightroom.todo.application.service.task.planning.TaskDueDateRegisterService
import net.brightroom.todo.application.service.task.planning.TaskPriorityRegisterService
import net.brightroom.todo.presentation.endpoint.exceptionController
import net.brightroom.todo.presentation.endpoint.task.content.taskContentModifyController
import net.brightroom.todo.presentation.endpoint.task.crateTaskController
import net.brightroom.todo.presentation.endpoint.task.lifecycle.taskLifecycleRegisterController
import net.brightroom.todo.presentation.endpoint.task.planning.taskDueDateRegisterController
import net.brightroom.todo.presentation.endpoint.task.planning.taskPriorityRegisterController
import net.brightroom.todo.presentation.endpoint.task.taskController

fun Application.routingConfigure(
    @Property("ktor.environment") environment: Environment,
) {
    install(ContentNegotiation) {
        jsonIo(CustomJson)
    }

    install(Resources)

    if (environment.isDeployment()) {
        install(KHealth)
    }

    install(StatusPages) {
        exceptionController()
    }

    val createTaskScenario: CreateTaskScenario by dependencies
    val taskService: TaskService by dependencies
    val taskContentRegisterService: TaskContentRegisterService by dependencies
    val taskPriorityRegisterService: TaskPriorityRegisterService by dependencies
    val taskDueDateRegisterService: TaskDueDateRegisterService by dependencies
    val taskLifecycleRegisterService: TaskLifecycleRegisterService by dependencies

    routing {
        route("/api/v1") {
            route("/task") {
                crateTaskController(createTaskScenario)
                taskController(taskService)
                taskContentModifyController(taskContentRegisterService, taskService)
                taskPriorityRegisterController(taskPriorityRegisterService, taskService)
                taskDueDateRegisterController(taskDueDateRegisterService, taskService)
                taskLifecycleRegisterController(taskLifecycleRegisterService, taskService)
            }
        }
    }
}
