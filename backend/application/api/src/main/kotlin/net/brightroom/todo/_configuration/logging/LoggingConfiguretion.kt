package net.brightroom.todo._configuration.logging

import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.calllogging.processingTimeMillis
import io.ktor.server.plugins.di.annotations.Property
import io.ktor.server.plugins.doublereceive.DoubleReceive
import io.ktor.server.plugins.origin
import io.ktor.server.request.contentType
import io.ktor.server.request.header
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.request.receiveText
import io.ktor.server.response.header
import kotlinx.coroutines.runBlocking
import org.slf4j.event.Level
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Application.configure(
    @Property("ktor.development") isDevelopment: Boolean,
) {
    install(DoubleReceive)

    install(CallId) {
        header(HttpHeaders.XRequestId)
        generate {
            Uuid.random().toString()
        }

        verify { callId: String ->
            if (callId.isEmpty()) return@verify false
            runCatching { Uuid.parse(callId) }.isSuccess
        }
    }

    install(CallLogging) {
        level = Level.INFO
        callIdMdc("call-id")

        filter { call ->
            val path = call.request.path()
            path.startsWith("/v1")
        }

        format { call ->
            val builder = StringBuilder()

            val remoteHost = call.request.origin.remoteHost
            builder.append("remoteHost:$remoteHost ")

            val serverHost = call.request.origin.serverHost
            val serverPort = call.request.origin.serverPort
            val host = "$serverHost${if (serverPort != 80) ":$serverPort" else ""}"
            builder.append("host:$host ")

            val requestUrl = call.request.path()
            builder.append("requestUrl:$requestUrl ")

            val httpMethod = call.request.httpMethod.value
            builder.append("httpMethod:$httpMethod ")

            val contentType = call.request.contentType()
            builder.append("contentType:$contentType ")

            val status = call.response.status()
            builder.append("status:$status ")

            val size = call.response.headers[HttpHeaders.ContentLength]?.toInt() ?: 0
            builder.append("size:$size ")

            val processingTimeMillis = call.processingTimeMillis()
            builder.append("processingTimeMillis:${processingTimeMillis}ms ")

            val authorization = call.request.headers["Authorization"] ?: "none"
            builder.append("authorization:$authorization ")

            val userAgent = call.request.headers["User-Agent"]
            builder.append("userAgent:$userAgent ")

            if (isDevelopment) {
                builder.appendLine()

                val requestBody = runBlocking { call.receiveText() }
                builder.appendLine("requestLog:")
                builder.append(requestBody)
            }

            builder.toString()
        }
    }
}
