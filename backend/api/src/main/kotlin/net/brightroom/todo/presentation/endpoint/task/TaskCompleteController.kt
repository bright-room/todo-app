package net.brightroom.todo.presentation.endpoint.task

import io.github.smiley4.ktoropenapi.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import net.brightroom.todo.application.service.task.TaskCompleteService
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.domain.problem.AlreadyCompletedException

fun Route.taskCompleteController(
    taskCompleteService: TaskCompleteService,
    taskService: TaskService,
) {
    post("/complete", {
        operationId = "タスクを完了する"
        tags = listOf("タスク")
        summary = "タスクを完了する"
        description = "タスクを完了済みにする"
        request { body<TaskCompleteRequest>() }
        response {
            HttpStatusCode.Created to {
                description = HttpStatusCode.Created.description
            }
            HttpStatusCode.BadRequest to {
                description = "不正なリクエスト"
            }
            HttpStatusCode.NotFound to {
                description = "タスクが存在しない"
            }
            HttpStatusCode.Conflict to {
                description = "タスクが既に完了済み"
            }
            HttpStatusCode.InternalServerError to {
                description = "サーバー起因による予期せぬエラー"
            }
        }
    }) {
        val request = call.receive<TaskCompleteRequest>()

        val task = taskService.get(request.id)
        val status = task.status
        if (status.is完了済み()) throw AlreadyCompletedException("Task is already completed")

        taskCompleteService.complete(request.id)

        call.respond(HttpStatusCode.Created)
    }

    post("/revert-to-completion", {
        operationId = "タスクを未完了状態に戻す"
        tags = listOf("タスク")
        summary = "タスクを未完了状態に戻す"
        description = "既に完了済みのタスクを未完了に戻す"
        request { body<TaskRevertCompletionRequest>() }
        response {
            HttpStatusCode.Created to {
                description = HttpStatusCode.Created.description
            }
            HttpStatusCode.BadRequest to {
                description = "不正なリクエスト"
            }
            HttpStatusCode.NotFound to {
                description = "タスクが存在しない"
            }
            HttpStatusCode.Conflict to {
                description = "タスクが未完了状態"
            }
            HttpStatusCode.InternalServerError to {
                description = "サーバー起因による予期せぬエラー"
            }
        }
    }) {
        val request = call.receive<TaskCompleteRequest>()

        val task = taskService.get(request.id)
        val status = task.status
        if (!status.is完了済み()) throw AlreadyCompletedException("Task is not completed")

        taskCompleteService.revertCompletion(request.id)

        call.respond(HttpStatusCode.Created)
    }
}
