package net.brightroom.todo.presentation.endpoint

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.log
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.request.path
import io.ktor.server.response.respond
import net.brightroom.todo.domain.policy.exception.AlreadyCompletedException
import net.brightroom.todo.domain.policy.exception.AlreadyOpenedException
import net.brightroom.todo.domain.policy.exception.ResourceNotFoundException

fun StatusPagesConfig.exceptionController() {
    fun buildErrorResponse(
        status: HttpStatusCode,
        call: ApplicationCall,
        e: Exception,
    ): ErrorResponse =
        ErrorResponse(
            title = status.description,
            status = status.value,
            detail = e.message ?: "No message available",
            instance = call.request.path(),
        )

    suspend fun badRequestResponse(
        call: ApplicationCall,
        e: Exception,
    ) {
        val status = HttpStatusCode.BadRequest
        val response = buildErrorResponse(status, call, e)

        call.application.log.warn(response.detail)
        call.respond(status, response)
    }

    suspend fun notFoundResponse(
        call: ApplicationCall,
        e: Exception,
    ) {
        val status = HttpStatusCode.NotFound
        val response = buildErrorResponse(status, call, e)

        call.application.log.warn(response.detail)
        call.respond(status, response)
    }

    suspend fun conflictResponse(
        call: ApplicationCall,
        e: Exception,
    ) {
        val status = HttpStatusCode.Conflict
        val response = buildErrorResponse(status, call, e)

        call.application.log.warn(response.detail)
        call.respond(status, response)
    }

    suspend fun internalServerErrorResponse(
        call: ApplicationCall,
        e: Exception,
    ) {
        val status = HttpStatusCode.InternalServerError
        val response = buildErrorResponse(status, call, e)

        call.application.log.error(response.detail, e)
        call.respond(status, response)
    }

    exception<Exception> { call, cause ->
        when (cause) {
            is IllegalArgumentException, is BadRequestException -> badRequestResponse(call, cause)
            is ResourceNotFoundException -> notFoundResponse(call, cause)
            is AlreadyCompletedException, is AlreadyOpenedException -> conflictResponse(call, cause)
            else -> internalServerErrorResponse(call, cause)
        }
    }
}
