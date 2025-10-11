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
import net.brightroom._extensions.kotlinx.serialization.CustomJson
import net.brightroom.todo._configuration.Environment

fun Application.openApiConfigure(
    @Property("ktor.environment") environment: Environment,
) {
    if (!environment.isProduction()) {
        install(OpenApi) {
            outputFormat = OutputFormat.JSON

            schemas {
                generator = SchemaGenerator.kotlinx(json = CustomJson)
            }
            examples {
                exampleEncoder = ExampleEncoder.kotlinx(CustomJson)
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
        if (!environment.isProduction()) {
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
