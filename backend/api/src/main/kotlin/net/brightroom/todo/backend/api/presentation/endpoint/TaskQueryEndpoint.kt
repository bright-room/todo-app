package net.brightroom.todo.backend.api.presentation.endpoint

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import net.brightroom.todo.backend.api.application.service.TaskQueryService
import net.brightroom.todo.shared.domain.model.TaskId
import net.brightroom.todo.shared.domain.problem.TaskNotFoundException

/**
 * タスク参照エンドポイント
 */
fun Application.configureTaskQueryRouting(taskQueryService: TaskQueryService) {
    routing {
        /**
         * 全タスク取得
         * GET /api/v1/tasks
         */
        get("/api/v1/tasks") {
            try {
                val tasks = taskQueryService.listAll()
                call.respond(HttpStatusCode.OK, tasks)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "タスクの取得に失敗しました"),
                )
            }
        }

        /**
         * 指定タスク取得
         * GET /api/v1/tasks/{id}
         */
        get("/api/v1/tasks/{id}") {
            try {
                val taskIdParam = call.parameters["id"]
                if (taskIdParam.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "タスクIDが必要です"))
                    return@get
                }

                val taskId = TaskId.of(taskIdParam)
                val task = taskQueryService.find(taskId)
                call.respond(HttpStatusCode.OK, task)
            } catch (e: TaskNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "タスクが見つかりません"))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "タスクの取得に失敗しました"),
                )
            }
        }

        /**
         * 完了済みタスク取得
         * GET /api/v1/tasks/completed
         */
        get("/api/v1/tasks/completed") {
            try {
                val tasks = taskQueryService.findCompleted()
                call.respond(HttpStatusCode.OK, tasks)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "完了済みタスクの取得に失敗しました"),
                )
            }
        }

        /**
         * 未完了タスク取得
         * GET /api/v1/tasks/incomplete
         */
        get("/api/v1/tasks/incomplete") {
            try {
                val tasks = taskQueryService.findIncomplete()
                call.respond(HttpStatusCode.OK, tasks)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "未完了タスクの取得に失敗しました"),
                )
            }
        }

        /**
         * 期限切れタスク取得
         * GET /api/v1/tasks/overdue
         */
        get("/api/v1/tasks/overdue") {
            try {
                val tasks = taskQueryService.findOverdue()
                call.respond(HttpStatusCode.OK, tasks)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "期限切れタスクの取得に失敗しました"),
                )
            }
        }
    }
}
