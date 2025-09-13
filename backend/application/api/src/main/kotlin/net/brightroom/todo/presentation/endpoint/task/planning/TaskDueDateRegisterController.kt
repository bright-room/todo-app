@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.presentation.endpoint.task.planning

import io.github.smiley4.ktoropenapi.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import kotlinx.datetime.LocalDate
import net.brightroom._extensions.kotlinx.datetime.now
import net.brightroom.todo._configuration.apispec.ResponseSpec
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.application.service.task.planning.TaskDueDateRegisterService
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.due.DueDateFactory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun Route.taskDueDateRegisterController(
    taskDueDateRegisterService: TaskDueDateRegisterService,
    taskService: TaskService,
) {
    post("/planning/due-date/register", {
        operationId = "タスクの期限日登録"
        tags(listOf("タスクプラン"))
        summary = "タスクの期限日登録"
        description = "既存タスクの期限日を登録する"
        request {
            body<TaskDueDateRegisterRequest> {
                example("リクエスト例") {
                    value =
                        TaskDueDateRegisterRequest(
                            Id(Uuid.random()),
                            DueDateFactory.create(LocalDate.now()),
                        )
                    summary = "タスクの期限日登録のリクエスト例"
                    description = "既存タスクの期限日を登録する場合のリクエスト例。"
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
        val request = call.receive<TaskDueDateRegisterRequest>()

        val dueDate = request.dueDate
        if (!dueDate.is期限日がセット済み()) throw IllegalArgumentException("DueDate is not set")

        taskService.getBy(request.id)
        taskDueDateRegisterService.clear(request.id)
        taskDueDateRegisterService.register(request.dueDate, request.id)

        call.respond(HttpStatusCode.OK)
    }

    post("/planning/due-date/clear", {
        operationId = "タスクの登録済み期限日削除"
        tags(listOf("タスクプラン"))
        summary = "タスクの登録済み期限日削除"
        description = "既存タスクの登録済み期限日を削除する"
        request {
            body<TaskDueDateClearRequest> {
                example("リクエスト例") {
                    value =
                        TaskDueDateClearRequest(Id(Uuid.random()))
                    summary = "タスクの登録済み期限日削除のリクエスト例"
                    description = "既存タスクの登録済み期限日を削除する場合のリクエスト例。"
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
        val request = call.receive<TaskDueDateClearRequest>()

        val task = taskService.getBy(request.id)

        val planning = task.planning
        val dueDate = planning.dueDate
        if (!dueDate.is期限日がセット済み()) throw IllegalArgumentException("DueDate is not set")

        taskDueDateRegisterService.clear(request.id)

        call.respond(HttpStatusCode.OK)
    }
}
