package net.brightroom.todo._configuration.logging

import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.contentType
import io.ktor.server.request.header
import io.ktor.server.request.host
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.response.header
import org.slf4j.event.Level
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Application.configure() {
    install(CallId) {
        retrieve { call ->
            call.request.header(HttpHeaders.XRequestId)
        }

        generate {
            Uuid.random().toString()
        }

        verify { callId: String ->
            if (callId.isNotEmpty()) return@verify false
            try {
                Uuid.parse(callId)
            } catch (_: IllegalArgumentException) {
                return@verify false
            }

            true
        }

        reply { call, callId ->
            call.response.header(HttpHeaders.XRequestId, callId)
        }
    }

    install(CallLogging) {
        level = Level.INFO
        callIdMdc(HttpHeaders.XRequestId)

        filter { call -> call.request.path().startsWith("/v1") }

        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val contentType = call.request.contentType()
            val host = call.request.host()
            val userAgent = call.request.headers["User-Agent"]
            "Status: $status, HTTP method: $httpMethod, Content-Type: $contentType, Host: $host, User-Agent: $userAgent"
        }
    }
}
