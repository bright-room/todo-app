@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.presentation.endpoint.task

import io.github.smiley4.ktoropenapi.resources.get
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import net.brightroom._extensions.kotlinx.datetime.now
import net.brightroom.todo._configuration.apispec.ResponseSpec
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.domain.model.Task
import net.brightroom.todo.domain.model.Tasks
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.content.Description
import net.brightroom.todo.domain.model.content.Title
import net.brightroom.todo.domain.model.identity.CreatedTime
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.identity.Identity
import net.brightroom.todo.domain.model.lifecycle.Lifecycle
import net.brightroom.todo.domain.model.lifecycle.Status
import net.brightroom.todo.domain.model.lifecycle.complete.CompletedTimeFactory
import net.brightroom.todo.domain.model.planning.Planning
import net.brightroom.todo.domain.model.planning.Priority
import net.brightroom.todo.domain.model.planning.due.DueDateFactory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Resource("")
private data class TaskRequest(
    val id: Id,
)

@Resource("list")
private class TaskListRequest

fun Route.taskController(taskService: TaskService) {
    get<TaskRequest>({
        operationId = "タスク取得"
        tags(listOf("タスク"))
        summary = "タスク取得"
        description = "IDに紐づくタスクを取得する"
        request {
            queryParameter<String>("id") {
                example("リクエスト例") {
                    value = Uuid.random().toString()
                    summary = "タスク取得のリクエスト例"
                    description = "IDに紐づくタスクを取得する場合のリクエスト例。"
                }
            }
        }
        response {
            HttpStatusCode.OK to {
                description = ResponseSpec.OK.DESCRIPTION
                body<Task> {
                    example("レスポンス例1") {
                        value =
                            Task(
                                Identity(Id(Uuid.random()), CreatedTime.now()),
                                Content(Title("タスク名"), Description("タスクの説明")),
                                Planning(),
                                Lifecycle(),
                            )
                        summary = "タスク取得のレスポンス例1"
                        description = "IDに紐づくタスクを取得する場合のレスポンス例。"
                    }
                    example("レスポンス例2") {
                        value =
                            Task(
                                Identity(Id(Uuid.random()), CreatedTime.now()),
                                Content(Title("タスク名"), Description("タスクの説明")),
                                Planning(DueDateFactory.create(LocalDate.now()), Priority.Low),
                                Lifecycle(Status.完了, CompletedTimeFactory.create(LocalDateTime.now())),
                            )
                        summary = "タスク取得のレスポンス例2"
                        description = "IDに紐づくタスクを取得する場合のレスポンス例。"
                    }
                }
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
    }) { request ->
        val task = taskService.getBy(request.id)
        call.respond(HttpStatusCode.OK, task)
    }

    get<TaskListRequest>({
        operationId = "タスク一覧取得"
        tags(listOf("タスク"))
        summary = "タスク一覧取得"
        description = "タスクの一覧を取得する"
        request {}
        response {
            HttpStatusCode.OK to {
                description = ResponseSpec.OK.DESCRIPTION
                body<Tasks> {
                    example("レスポンス例") {
                        value =
                            Tasks(
                                listOf(
                                    Task(
                                        Identity(Id(Uuid.random()), CreatedTime.now()),
                                        Content(Title("タスク名"), Description("タスクの説明")),
                                        Planning(),
                                        Lifecycle(),
                                    ),
                                    Task(
                                        Identity(Id(Uuid.random()), CreatedTime.now()),
                                        Content(Title("タスク名"), Description("タスクの説明")),
                                        Planning(DueDateFactory.create(LocalDate.now()), Priority.Low),
                                        Lifecycle(Status.完了, CompletedTimeFactory.create(LocalDateTime.now())),
                                    ),
                                ),
                            )
                        summary = "タスク一覧取得のレスポンス例"
                        description = "タスクの一覧を取得する場合のレスポンス例。"
                    }
                }
            }
            HttpStatusCode.InternalServerError to {
                description = ResponseSpec.InternalServerError.DESCRIPTION
            }
        }
    }) { _ ->
        val tasks = taskService.listAll()
        call.respond(HttpStatusCode.OK, tasks)
    }
}
