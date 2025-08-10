package net.brightroom.todo.presentation.endpoint

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.request.path
import io.ktor.server.response.respond
import net.brightroom.todo.domain.policy.exception.AlreadyCompletedException
import net.brightroom.todo.domain.policy.exception.ResourceNotFoundException

fun StatusPagesConfig.exceptionController() {
    exception<IllegalArgumentException> { call, cause ->
        val status = HttpStatusCode.BadRequest
        val uri = call.request.path()
        val detailMessage = cause.message ?: "No message available"

        val response =
            ErrorResponse(
                title = status.description,
                status = status.value,
                detail = detailMessage,
                instance = uri,
            )
        call.respond(status, response)
    }

    exception<ResourceNotFoundException> { call, cause ->
        val status = HttpStatusCode.NotFound
        val uri = call.request.path()
        val detailMessage = cause.message ?: "No message available"

        val response =
            ErrorResponse(
                title = status.description,
                status = status.value,
                detail = detailMessage,
                instance = uri,
            )
        call.respond(status, response)
    }

    exception<AlreadyCompletedException> { call, cause ->
        val status = HttpStatusCode.Conflict
        val uri = call.request.path()
        val detailMessage = cause.message ?: "No message available"

        val response =
            ErrorResponse(
                title = status.description,
                status = status.value,
                detail = detailMessage,
                instance = uri,
            )
        call.respond(status, response)
    }

    exception<Exception> { call, cause ->
        val status = HttpStatusCode.InternalServerError
        val uri = call.request.path()
        val detailMessage = cause.message ?: "No message available"

        val response =
            ErrorResponse(
                title = status.description,
                status = status.value,
                detail = detailMessage,
                instance = uri,
            )
        call.respond(status, response)
    }
}
