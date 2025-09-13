@file:OptIn(ExperimentalSerializationApi::class)

package net.brightroom.todo._configuration

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
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import net.brightroom.todo.application.scenario.task.TaskRegisterScenario
import net.brightroom.todo.presentation.endpoint.exceptionController
import net.brightroom.todo.presentation.endpoint.task.taskRegisterController

fun Application.configure(
    @Property("ktor.development") isDevelopment: Boolean,
) {
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

    install(Resources)

    install(StatusPages) {
        exceptionController()
    }

    install(KHealth)

    val taskRegisterScenario: TaskRegisterScenario by dependencies

    routing {
        route("/api") {
            route("/task") {
                taskRegisterController(taskRegisterScenario)
            }
        }
    }
}
