package net.brightroom.todo

import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.cio.EngineMain
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.plugins.di.resolve
import io.ktor.server.plugins.doublereceive.DoubleReceive
import io.ktor.server.request.contentType
import io.ktor.server.request.header
import io.ktor.server.request.host
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.resources.Resources
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.codec.EnumCodec
import kotlinx.serialization.json.Json
import net.brightroom.todo.application.repository.task.TaskCompleteRepository
import net.brightroom.todo.application.repository.task.TaskCreateRepository
import net.brightroom.todo.application.repository.task.TaskRepository
import net.brightroom.todo.application.repository.task.content.TaskContentRegisterRepository
import net.brightroom.todo.application.service.task.TaskCompleteService
import net.brightroom.todo.application.service.task.TaskCreateService
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService
import net.brightroom.todo.domain.model.task.Status
import net.brightroom.todo.infrastructure.datasource.task.TaskCompleteDataSource
import net.brightroom.todo.infrastructure.datasource.task.TaskCreateDataSource
import net.brightroom.todo.infrastructure.datasource.task.TaskDataSource
import net.brightroom.todo.infrastructure.datasource.task.content.TaskContentRegisterDataSource
import net.brightroom.todo.presentation.endpoint.task.content.taskContentModifyController
import net.brightroom.todo.presentation.endpoint.task.taskCompleteController
import net.brightroom.todo.presentation.endpoint.task.taskController
import net.brightroom.todo.presentation.endpoint.task.taskCreateController
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabaseConfig
import org.slf4j.event.Level
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            },
        )
    }

    install(DoubleReceive)
    install(Resources)

    install(CallId) {
        retrieve { call ->
            call.request.header(HttpHeaders.XRequestId)
        }
        @OptIn(ExperimentalUuidApi::class)
        generate {
            Uuid.random().toString()
        }

        @OptIn(ExperimentalUuidApi::class)
        verify { callId: String ->
            if (callId.isNotEmpty()) return@verify false
            try {
                Uuid.parse(callId)
            } catch (e: IllegalArgumentException) {
                return@verify false
            }

            true
        }
    }
    install(CallLogging) {
        level = Level.INFO
        callIdMdc(HttpHeaders.XRequestId)
        filter { call ->
            call.request.path().startsWith("/v1")
        }
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val contentType = call.request.contentType()
            val host = call.request.host()
            val userAgent = call.request.headers["User-Agent"]
            "Status: $status, HTTP method: $httpMethod, Content-Type: $contentType, Host: $host, User-Agent: $userAgent"
        }
    }

    dependencies {
        provide<R2dbcDatabase> {
            val options =
                PostgresqlConnectionConfiguration
                    .builder()
                    .host("localhost")
                    .port(15432)
                    .database("todo")
                    .schema("todo_app")
                    .username("brapl")
                    .password("brapl")
                    .codecRegistrar(EnumCodec.builder().withEnum("task_status_type", Status::class.java).build())
                    .build()

            val cxFactory = PostgresqlConnectionFactory(options)

            R2dbcDatabase.connect(
                connectionFactory = cxFactory,
                databaseConfig =
                    R2dbcDatabaseConfig {
                        explicitDialect = PostgreSQLDialect()
                    },
            )
        }

        provide<TaskRepository> { TaskDataSource(resolve()) }
        provide<TaskCreateRepository> { TaskCreateDataSource(resolve()) }
        provide<TaskContentRegisterRepository> { TaskContentRegisterDataSource(resolve()) }
        provide<TaskCompleteRepository> { TaskCompleteDataSource(resolve()) }

        provide<TaskService> { TaskService(resolve()) }
        provide<TaskCreateService> { TaskCreateService(resolve()) }
        provide<TaskContentRegisterService> { TaskContentRegisterService(resolve()) }
        provide<TaskCompleteService> { TaskCompleteService(resolve()) }
    }

    val taskService: TaskService by dependencies
    val taskCreateService: TaskCreateService by dependencies
    val taskContentRegisterService: TaskContentRegisterService by dependencies
    val taskCompleteService: TaskCompleteService by dependencies

    routing {
        route("/v1/task") {
            taskController(taskService)
            taskCreateController(taskCreateService, taskContentRegisterService)
            taskContentModifyController(taskContentRegisterService, taskService)
            taskCompleteController(taskCompleteService, taskService)
        }
    }
}
