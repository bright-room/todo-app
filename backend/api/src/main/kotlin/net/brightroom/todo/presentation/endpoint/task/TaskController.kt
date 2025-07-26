package net.brightroom.todo.presentation.endpoint.task

import io.github.smiley4.ktoropenapi.resources.get
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.domain.model.task.Task
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.Tasks

@Resource("")
private data class TaskRequest(
    val id: TaskId,
)

@Resource("list")
private class TaskListRequest

fun Route.taskController(taskService: TaskService) {
    get<TaskRequest>({
        operationId = "タスクの取得"
        tags = listOf("タスク")
        summary = "タスクの取得"
        description = "タスクを取得する"
        response {
            HttpStatusCode.OK to {
                description = HttpStatusCode.OK.description
                body<Task> {}
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
        val task = taskService.get(it.id)
        call.respond(HttpStatusCode.OK, task)
    }

    get<TaskListRequest>({
        operationId = "タスク一覧の取得"
        tags = listOf("タスク")
        summary = "タスク一覧の取得"
        description = "タスクの一覧を取得する"
        response {
            HttpStatusCode.OK to {
                description = HttpStatusCode.OK.description
                body<Tasks> {}
            }
            HttpStatusCode.InternalServerError to {
                description = "サーバー起因による予期せぬエラー"
            }
        }
    }) {
        val tasks = taskService.listAll()
        call.respond(HttpStatusCode.OK, tasks)
    }
}
