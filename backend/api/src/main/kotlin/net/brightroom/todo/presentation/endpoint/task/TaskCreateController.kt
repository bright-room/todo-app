package net.brightroom.todo.presentation.endpoint.task

import io.github.smiley4.ktoropenapi.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import net.brightroom.todo.application.service.task.TaskCreateService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService
import net.brightroom.todo.domain.model.task.content.Content

fun Route.taskCreateController(
    taskCreteService: TaskCreateService,
    taskContentRegisterService: TaskContentRegisterService,
) {
    post("/create", {
        operationId = "タスク作成"
        tags = listOf("タスク")
        summary = "タスク作成"
        description = "タスクを作成する"
        request { body<TaskCreateRequest>() }
        response {
            HttpStatusCode.Created to {
                description = HttpStatusCode.Created.description
            }
            HttpStatusCode.BadRequest to {
                description = "不正なリクエスト"
            }
            HttpStatusCode.InternalServerError to {
                description = "サーバー起因による予期せぬエラー"
            }
        }
    }) {
        val request = call.receive<TaskCreateRequest>()

        val id = taskCreteService.create()
        val content = Content(request.title, request.description)

        taskContentRegisterService.register(id, content)

        call.respond(HttpStatusCode.Created)
    }
}
