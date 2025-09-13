@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.presentation.endpoint.task.planning

import io.github.smiley4.ktoropenapi.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import net.brightroom.todo._configuration.apispec.ResponseSpec
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.application.service.task.planning.TaskPriorityRegisterService
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.Priority
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun Route.taskPriorityRegisterController(
    taskPriorityRegisterService: TaskPriorityRegisterService,
    taskService: TaskService,
) {
    post("/planning/priority/register", {
        operationId = "タスクの優先度登録"
        tags(listOf("タスクプラン"))
        summary = "タスクの優先度登録"
        description = "既存タスクの優先度を登録する"
        request {
            body<TaskPriorityRegisterRequest> {
                example("リクエスト例") {
                    value =
                        TaskPriorityRegisterRequest(
                            Id(Uuid.random()),
                            Priority.High,
                        )
                    summary = "タスクの優先度登録のリクエスト例"
                    description = "既存の既存タスクの優先度を登録する場合のリクエスト例。"
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
        val request = call.receive<TaskPriorityRegisterRequest>()

        taskService.getBy(request.id)
        taskPriorityRegisterService.register(request.priority, request.id)

        call.respond(HttpStatusCode.OK)
    }
}
