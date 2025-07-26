package net.brightroom.todo._configuration

import dev.hayden.KHealth
import io.ktor.serialization.kotlinx.json.jsonIo
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.plugins.doublereceive.DoubleReceive
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import net.brightroom.todo.application.service.task.TaskCompleteService
import net.brightroom.todo.application.service.task.TaskCreateService
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService
import net.brightroom.todo.presentation.endpoint.exceptionController
import net.brightroom.todo.presentation.endpoint.task.content.taskContentModifyController
import net.brightroom.todo.presentation.endpoint.task.taskCompleteController
import net.brightroom.todo.presentation.endpoint.task.taskController
import net.brightroom.todo.presentation.endpoint.task.taskCreateController

@OptIn(ExperimentalSerializationApi::class)
fun Application.configure() {
    install(ContentNegotiation) {
        jsonIo(
            Json {
                prettyPrint = true
                isLenient = true
                classDiscriminatorMode = ClassDiscriminatorMode.NONE
            },
        )
    }

    install(DoubleReceive)
    install(Resources)

    install(StatusPages) {
        exceptionController()
    }

    install(KHealth)

    routing {
        val taskService: TaskService by dependencies
        val taskCreateService: TaskCreateService by dependencies
        val taskContentRegisterService: TaskContentRegisterService by dependencies
        val taskCompleteService: TaskCompleteService by dependencies

        route("/v1") {
            route("/task") {
                taskController(taskService)
                taskCreateController(taskCreateService, taskContentRegisterService)
                taskContentModifyController(taskContentRegisterService, taskService)
                taskCompleteController(taskCompleteService, taskService)
            }
        }
    }
}
