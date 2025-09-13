@file:OptIn(ExperimentalSerializationApi::class)

package net.brightroom.todo._configuration.apispec

import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.config.ExampleEncoder
import io.github.smiley4.ktoropenapi.config.OutputFormat
import io.github.smiley4.ktoropenapi.config.SchemaGenerator
import io.github.smiley4.ktoropenapi.openApi
import io.github.smiley4.ktorredoc.redoc
import io.github.smiley4.ktorswaggerui.swaggerUI
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.di.annotations.Property
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

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

    if (isDevelopment) {
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
    }

    routing {
        if (isDevelopment) {
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
}
