@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.presentation.endpoint.task

import io.github.smiley4.ktoropenapi.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import net.brightroom.todo._configuration.apispec.ResponseSpec
import net.brightroom.todo.application.scenario.task.CreateTaskScenario
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.content.Description
import net.brightroom.todo.domain.model.content.Title
import net.brightroom.todo.domain.model.identity.Id
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun Route.crateTaskController(createTaskScenario: CreateTaskScenario) {
    post("/create", {
        operationId = "タスク作成"
        tags(listOf("タスク作成"))
        summary = "タスク作成"
        description = "新規タスクを作成する"
        request {
            body<CreateTaskRequest> {
                example("リクエスト例") {
                    value =
                        CreateTaskRequest(
                            Title("タスク名"),
                            Description("タスクの説明"),
                        )
                    summary = "タスク作成のリクエスト例"
                    description = "新規でタスクを作成する場合のリクエスト例。"
                }
            }
        }
        response {
            HttpStatusCode.Created to {
                description = ResponseSpec.Created.DESCRIPTION
                body<CreateTaskResponse> {
                    example("レスポンス例") {
                        value = CreateTaskResponse(Id(Uuid.random()))
                        summary = "タスク作成のレスポンス例"
                        description = "新規でタスクを作成する場合のレスポンス例。"
                    }
                }
            }
            HttpStatusCode.BadRequest to {
                description = ResponseSpec.BadRequest.DESCRIPTION
            }
            HttpStatusCode.InternalServerError to {
                description = ResponseSpec.InternalServerError.DESCRIPTION
            }
        }
    }) {
        val request = call.receive<CreateTaskRequest>()

        val violations = request.validate()
        if (!violations.isValid) {
            throw IllegalArgumentException(violations.toString())
        }

        val content = Content(request.title, request.description)
        val id = createTaskScenario.register(content)

        val response = CreateTaskResponse(id)
        call.respond(HttpStatusCode.Created, response)
    }
}
