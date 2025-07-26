@file:OptIn(ExperimentalSerializationApi::class)

package net.brightroom.todo._configuration

import dev.hayden.KHealth
import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.config.ExampleEncoder
import io.github.smiley4.ktoropenapi.config.OutputFormat
import io.github.smiley4.ktoropenapi.config.SchemaGenerator
import io.github.smiley4.ktoropenapi.openApi
import io.github.smiley4.ktorredoc.redoc
import io.github.smiley4.ktorswaggerui.swaggerUI
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
import kotlinx.serialization.json.JsonNamingStrategy
import net.brightroom.todo.application.service.task.TaskCompleteService
import net.brightroom.todo.application.service.task.TaskCreateService
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService
import net.brightroom.todo.presentation.endpoint.exceptionController
import net.brightroom.todo.presentation.endpoint.task.content.taskContentModifyController
import net.brightroom.todo.presentation.endpoint.task.taskCompleteController
import net.brightroom.todo.presentation.endpoint.task.taskController
import net.brightroom.todo.presentation.endpoint.task.taskCreateController

fun Application.configure() {
    val kotlinxJson =
        Json {
            prettyPrint = true
            isLenient = true
            encodeDefaults = true
            classDiscriminatorMode = ClassDiscriminatorMode.NONE
            namingStrategy = JsonNamingStrategy.SnakeCase
        }

    install(ContentNegotiation) {
        jsonIo(kotlinxJson)
    }

    install(DoubleReceive)
    install(Resources)

    install(StatusPages) {
        exceptionController()
    }

    install(KHealth)

    install(OpenApi) {
        outputFormat = OutputFormat.JSON

        schemas {
            generator = SchemaGenerator.kotlinx(json = kotlinxJson)
        }
        examples {
            exampleEncoder = ExampleEncoder.kotlinx(kotlinxJson)
        }

        info {
            title = "TODO API"
            description =
                "RESTful API for managing tasks in a TODO application. Supports creating, updating, completing tasks and managing task content."
            version = "1.0.0"

            contact {
                name = "kukv"
                email = "example@example.com"
                url = "https://bright-room.net"
            }
        }

        server {
            url = "http://localhost:8080"
            description = "Local server"
        }
    }

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

        route("api.json") {
            openApi()
        }

        route("swagger-ui") {
            swaggerUI("/api.json")
        }

        route("redoc-ui") {
            redoc("/api.json")
        }
    }
}
