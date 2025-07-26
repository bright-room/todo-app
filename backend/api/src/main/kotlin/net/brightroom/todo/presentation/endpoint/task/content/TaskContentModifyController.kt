package net.brightroom.todo.presentation.endpoint.task.content

import io.github.smiley4.ktoropenapi.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService

fun Route.taskContentModifyController(
    taskContentRegisterService: TaskContentRegisterService,
    taskService: TaskService,
) {
    post("/modify", {
        operationId = "タスク内容の変更"
        tags = listOf("タスク")
        summary = "タスク内容の変更"
        description = "タスク内容を変更する"
        request { body<TaskContentModifyRequest>() }
        response {
            HttpStatusCode.OK to {
                description = HttpStatusCode.OK.description
            }
            HttpStatusCode.BadRequest to {
                description = "不正なリクエスト"
            }
            HttpStatusCode.NotFound to {
                description = "タスクが存在しない"
            }
            HttpStatusCode.InternalServerError to {
                description = "サーバー起因による予期せぬエラー"
            }
        }
    }) {
        val request = call.receive<TaskContentModifyRequest>()

        taskService.get(request.id)
        taskContentRegisterService.register(request.id, request.content)

        call.respond(HttpStatusCode.Created)
    }
}
