@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.presentation.endpoint.task.content

import io.github.smiley4.ktoropenapi.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import net.brightroom.todo._configuration.apispec.ResponseSpec
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.content.Description
import net.brightroom.todo.domain.model.content.Title
import net.brightroom.todo.domain.model.identity.Id
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun Route.taskContentModifyController(
    taskContentRegisterService: TaskContentRegisterService,
    taskService: TaskService,
) {
    post("/content/modify", {
        operationId = "タスクコンテンツの編集"
        tags(listOf("タスクコンテンツ"))
        summary = "タスクコンテンツの編集"
        description = "既存タスクのコンテンツを編集する"
        request {
            body<TaskContentModifyRequest> {
                example("リクエスト例") {
                    value =
                        TaskContentModifyRequest(
                            Id(Uuid.random()),
                            Content(Title("編集後のタスクタイトル"), Description("編集後のタスク説明")),
                        )
                    summary = "タスクコンテンツの編集のリクエスト例"
                    description = "既存のタスクコンテンツを編集する場合のリクエスト例。"
                }
            }
        }
        response {
            HttpStatusCode.OK to {
                description = ResponseSpec.OK.DESCRIPTION
            }
            HttpStatusCode.BadRequest to {
                description = ResponseSpec.BadRequest.DESCRIPTION
            }
            HttpStatusCode.NotFound to {
                description = ResponseSpec.NotFound.DESCRIPTION
            }
            HttpStatusCode.InternalServerError to {
                description = ResponseSpec.InternalServerError.DESCRIPTION
            }
        }
    }) {
        val request = call.receive<TaskContentModifyRequest>()

        val violations = request.validate()
        if (!violations.isValid) {
            throw IllegalArgumentException(violations.toString())
        }

        taskService.getBy(request.id)
        taskContentRegisterService.register(request.content, request.id)

        call.respond(HttpStatusCode.OK)
    }
}
