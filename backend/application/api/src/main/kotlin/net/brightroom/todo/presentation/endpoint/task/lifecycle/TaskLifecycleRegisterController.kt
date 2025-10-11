@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.presentation.endpoint.task.lifecycle

import io.github.smiley4.ktoropenapi.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import net.brightroom.todo._configuration.apispec.ResponseSpec
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.application.service.task.lifecycle.TaskLifecycleRegisterService
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.policy.exception.AlreadyCompletedException
import net.brightroom.todo.domain.policy.exception.AlreadyOpenedException
import net.brightroom.todo.presentation.endpoint.task.planning.TaskDueDateClearRequest
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun Route.taskLifecycleRegisterController(
    taskLifecycleRegisterService: TaskLifecycleRegisterService,
    taskService: TaskService,
) {
    post("/lifecycle/complete", {
        operationId = "未完了のタスクを完了済みにする"
        tags(listOf("タスクライフサイクル"))
        summary = "未完了のタスクを完了済みにする"
        description = "未完了の既存タスクを完了済みにする"
        request {
            body<TaskCompleteRequest> {
                example("リクエスト例") {
                    value =
                        TaskCompleteRequest(Id(Uuid.random()))
                    summary = "未完了のタスクを完了済みにするリクエスト例"
                    description = "既存の未完了タスクを完了済みにする場合のリクエスト例。"
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
            HttpStatusCode.Conflict to {
                description = "既に完了済み"
            }
            HttpStatusCode.InternalServerError to {
                description = ResponseSpec.InternalServerError.DESCRIPTION
            }
        }
    }) {
        val request = call.receive<TaskCompleteRequest>()

        val task = taskService.getBy(request.id)

        val lifecycle = task.lifecycle
        val status = lifecycle.status
        if (status.is完了済み()) throw AlreadyCompletedException("Task is already completed")

        taskLifecycleRegisterService.complete(request.id)

        call.respond(HttpStatusCode.OK)
    }

    post("/lifecycle/reopen", {
        operationId = "完了済みのタスクを再オープンする"
        tags(listOf("タスクライフサイクル"))
        summary = "完了済みのタスクを再オープンする"
        description = "完了済みのタスクを再オープンする"
        request {
            body<TaskCompleteRequest> {
                example("リクエスト例") {
                    value =
                        TaskCompleteRequest(Id(Uuid.random()))
                    summary = "完了済みのタスクを再オープンするリクエスト例"
                    description = "既存の完了済みタスクを再オープンする場合のリクエスト例。"
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
            HttpStatusCode.Conflict to {
                description = "既にオープン済み"
            }
            HttpStatusCode.InternalServerError to {
                description = ResponseSpec.InternalServerError.DESCRIPTION
            }
        }
    }) {
        val request = call.receive<TaskDueDateClearRequest>()

        val task = taskService.getBy(request.id)

        val lifecycle = task.lifecycle
        val status = lifecycle.status
        if (!status.is完了済み()) throw AlreadyOpenedException("Task is already opened")

        taskLifecycleRegisterService.reopen(request.id)

        call.respond(HttpStatusCode.OK)
    }
}
