package net.brightroom.todo.presentation.endpoint

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.jsonIo
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.config.mergeWith
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import net.brightroom.todo._configuration.testConfigure
import org.junit.jupiter.api.DynamicTest

fun endToEndTestApplication(block: EndToEndTestContext.() -> Unit): Collection<DynamicTest> {
    val context = EndToEndTestContext()
    context.block()

    val scenarios = context.scenarios
    val dynamicTests =
        scenarios.map { scenario ->
            DynamicTest.dynamicTest(scenario.name) {
                testApplication {
                    environment {
                        config = ApplicationConfig("application.yaml")
                        config = config.mergeWith(ApplicationConfig("application-test.yaml"))
                    }
                    application {
                        testConfigure()
                    }

                    this.startApplication()

                    try {
                        context.initialize(application)
                        scenario.execute(this)
                    } finally {
                        context.finalize(application)
                    }
                }
            }
        }

    return dynamicTests
}

class EndToEndTestContext(
    val scenarios: MutableList<TestingScenario> = mutableListOf(),
    var initialize: suspend Application.() -> Unit = {},
    var finalize: suspend Application.() -> Unit = {},
) {
    fun testingScenario(
        name: String,
        block: TestingScenario.Builder.() -> Unit,
    ) {
        scenarios += TestingScenario.Builder(name).apply(block).build()
    }

    fun initialize(block: suspend Application.() -> Unit) {
        initialize = block
    }

    fun finalize(block: suspend Application.() -> Unit) {
        finalize = block
    }
}

class TestingScenario(
    val name: String,
    private val given: Given,
    private val whenDo: WhenDo,
    private val then: Then,
) {
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun execute(app: ApplicationTestBuilder) {
        try {
            given.setup(app.application)

            val client =
                whenDo.client ?: app.createClient {
                    install(ContentNegotiation) {
                        jsonIo(
                            Json {
                                explicitNulls = false
                                prettyPrint = true
                                isLenient = true
                                encodeDefaults = true
                                classDiscriminatorMode = ClassDiscriminatorMode.NONE
                                namingStrategy = JsonNamingStrategy.SnakeCase
                            },
                        )
                    }
                }

            then.response = client.requestWithWhenDo(whenDo) {}
            then.assertion()
        } finally {
            given.tearDown(app.application)
        }
    }

    class Builder(private val name: String) {
        private var given: Given = Given()
        private var whenDo: WhenDo = WhenDo()
        private var then: Then = Then()

        fun given(block: Given.() -> Unit) = apply { given.apply(block) }

        fun whenDo(block: WhenDo.() -> Unit) = apply { whenDo.apply(block) }

        fun then(block: Then.() -> Unit) = apply { then.assertion = { then.block() } }

        fun build(): TestingScenario = TestingScenario(name, given, whenDo, then)
    }
}

class Given(var setup: suspend Application.() -> Unit = {}, var tearDown: suspend Application.() -> Unit = {}) {
    fun setup(block: suspend Application.() -> Unit) {
        setup = block
    }

    fun tearDown(block: suspend Application.() -> Unit) {
        tearDown = block
    }
}

class WhenDo(
    var path: String = "",
    var method: HttpMethod = HttpMethod.Get,
    val headers: HeadersBuilder = HeadersBuilder(),
    var queryParams: MutableMap<String, String> = mutableMapOf(),
    var contentType: ContentType = ContentType.Application.Json,
    var body: Any? = null,
    var client: HttpClient? = null,
) {
    fun header(
        name: String,
        value: String,
    ) {
        headers.append(name, value)
    }

    fun queryParam(
        name: String,
        value: String,
    ) {
        queryParams[name] = value
    }
}

class Then(var assertion: suspend () -> Unit = {}) {
    lateinit var response: HttpResponse
}

suspend inline fun HttpClient.requestWithWhenDo(
    whenDo: WhenDo,
    customize: HttpRequestBuilder.() -> Unit,
): HttpResponse =
    this.request {
        customize()
        url {
            path(whenDo.path)
        }
        method = whenDo.method

        val contentType = whenDo.contentType
        contentType(contentType)

        if (contentType == ContentType.Application.Json) {
            accept(contentType)
        }

        val headers = whenDo.headers.build()
        headers.names().forEach { name ->
            headers.getAll(name)?.forEach { header(name, it) }
        }

        val queryParams = whenDo.queryParams
        if (queryParams.isNotEmpty()) {
            queryParams.forEach { (k, v) -> parameter(k, v) }
        }

        whenDo.body?.let { setBody(it) }
    }
