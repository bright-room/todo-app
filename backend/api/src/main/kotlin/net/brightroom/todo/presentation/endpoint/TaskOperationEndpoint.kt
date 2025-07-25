package net.brightroom.todo.presentation.endpoint

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import net.brightroom.todo.backend.api.application.service.TaskCompleteService
import net.brightroom.todo.backend.api.application.service.TaskCreateService
import net.brightroom.todo.backend.api.application.service.TaskDeleteService
import net.brightroom.todo.backend.api.application.service.TaskUpdateService
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.problem.ResourceNotFoundException

/**
 * タスク作成リクエスト
 */
@Serializable
data class TaskCreateRequest(
    val title: String,
    val description: String = "",
    val dueDate: String,
)

/**
 * タスク更新リクエスト
 */
@Serializable
data class TaskUpdateRequest(
    val taskId: String,
    val title: String? = null,
    val description: String? = null,
    val dueDate: String? = null,
)

/**
 * タスク完了リクエスト
 */
@Serializable
data class TaskCompleteRequest(
    val taskId: String,
)

/**
 * タスク削除リクエスト
 */
@Serializable
data class TaskDeleteRequest(
    val taskId: String,
)

/**
 * タスク操作エンドポイント
 */
fun Application.configureTaskOperationRouting(
    taskCreateService: TaskCreateService,
    taskUpdateService: TaskUpdateService,
    taskCompleteService: TaskCompleteService,
    taskDeleteService: TaskDeleteService,
) {
    routing {
        /**
         * タスク作成
         * POST /api/v1/tasks/create
         */
        post("/api/v1/tasks/create") {
            try {
                val request = call.receive<TaskCreateRequest>()

                // リクエストバリデーション
                if (request.title.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "タスクタイトルが必要です"))
                    return@post
                }
                if (request.dueDate.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "期日が必要です"))
                    return@post
                }

                val createdTask =
                    taskCreateService.create(
                        title = request.title,
                        description = request.description,
                        dueDate = request.dueDate,
                    )
                call.respond(HttpStatusCode.Created, createdTask)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "タスクの作成に失敗しました: ${e.message}"),
                )
            }
        }

        /**
         * タスク更新
         * POST /api/v1/tasks/update
         */
        post("/api/v1/tasks/update") {
            try {
                val request = call.receive<TaskUpdateRequest>()

                // リクエストバリデーション
                if (request.taskId.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "タスクIDが必要です"))
                    return@post
                }

                val taskId = TaskId.of(request.taskId)
                val updatedTask =
                    taskUpdateService.update(
                        taskId = taskId,
                        title = request.title,
                        description = request.description,
                        dueDate = request.dueDate,
                    )
                call.respond(HttpStatusCode.OK, updatedTask)
            } catch (e: ResourceNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "タスクが見つかりません"))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "タスクの更新に失敗しました: ${e.message}"),
                )
            }
        }

        /**
         * タスク完了
         * POST /api/v1/tasks/complete
         */
        post("/api/v1/tasks/complete") {
            try {
                val request = call.receive<TaskCompleteRequest>()

                // リクエストバリデーション
                if (request.taskId.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "タスクIDが必要です"))
                    return@post
                }

                val taskId = TaskId.of(request.taskId)
                taskCompleteService.complete(taskId)
                call.respond(HttpStatusCode.OK, mapOf("message" to "タスクを完了しました"))
            } catch (e: ResourceNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "タスクが見つかりません"))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "タスクの完了処理に失敗しました: ${e.message}"),
                )
            }
        }

        /**
         * タスク未完了
         * POST /api/v1/tasks/uncomplete
         */
        post("/api/v1/tasks/uncomplete") {
            try {
                val request = call.receive<TaskCompleteRequest>()

                // リクエストバリデーション
                if (request.taskId.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "タスクIDが必要です"))
                    return@post
                }

                val taskId = TaskId.of(request.taskId)
                taskCompleteService.uncomplete(taskId)
                call.respond(HttpStatusCode.OK, mapOf("message" to "タスクを未完了にしました"))
            } catch (e: ResourceNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "タスクが見つかりません"))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "タスクの未完了処理に失敗しました: ${e.message}"),
                )
            }
        }

        /**
         * タスク削除
         * POST /api/v1/tasks/delete
         */
        post("/api/v1/tasks/delete") {
            try {
                val request = call.receive<TaskDeleteRequest>()

                // リクエストバリデーション
                if (request.taskId.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "タスクIDが必要です"))
                    return@post
                }

                val taskId = TaskId.of(request.taskId)
                taskDeleteService.delete(taskId)
                call.respond(HttpStatusCode.OK, mapOf("message" to "タスクを削除しました"))
            } catch (e: ResourceNotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "タスクが見つかりません"))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "タスクの削除に失敗しました: ${e.message}"),
                )
            }
        }
    }
}
