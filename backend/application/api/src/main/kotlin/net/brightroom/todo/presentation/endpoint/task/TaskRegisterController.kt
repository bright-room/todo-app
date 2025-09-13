package net.brightroom.todo.presentation.endpoint.task

import io.github.smiley4.ktoropenapi.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import net.brightroom.todo._configuration.apispec.ResponseSpec
import net.brightroom.todo.application.scenario.task.TaskRegisterScenario

fun Route.taskRegisterController(taskRegisterScenario: TaskRegisterScenario) {
    post("/task/register", {
        operationId = "タスク登録"
        tags(listOf("タスク"))
        summary = "タスク登録"
        description = "タスクを新規登録する"
        request {
            body<TaskRegisterRequest> {}
        }
        response {
            HttpStatusCode.Created to {
                description = ResponseSpec.Created.DESCRIPTION
                body<TaskRegisterResponse> {}
            }
            HttpStatusCode.BadRequest to {
                description = ResponseSpec.BadRequest.DESCRIPTION
            }
        }
    }) {
        val request = call.receive<TaskRegisterRequest>()

        val violations = request.validate()
        if (!violations.isValid) {
            throw IllegalArgumentException(violations.toString())
        }

        val id =
            taskRegisterScenario.register(
                request.content,
                request.planning,
                request.lifecycle,
                request.classification,
            )

        val response = TaskRegisterResponse(id)
        call.respond(HttpStatusCode.Created, response)
    }
}
