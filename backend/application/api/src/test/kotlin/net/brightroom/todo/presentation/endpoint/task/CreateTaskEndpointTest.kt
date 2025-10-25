@file:OptIn(kotlin.uuid.ExperimentalUuidApi::class)

package net.brightroom.todo.presentation.endpoint.task

import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.di.dependencies
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import net.brightroom.todo.infrastructure.datasource.task.TaskClearDataSource
import net.brightroom.todo.presentation.endpoint.endToEndTestApplication
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestFactory
import java.util.regex.Pattern
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Serializable
private data class CreateTaskDummyRequest(val title: String? = null, val description: String? = null)

@DisplayName("タスク作成のE2Eテスト")
class CreateTaskEndpointTest {
    @Tag("integration")
    @TestFactory
    fun run() =
        endToEndTestApplication {
            finalize {
                val taskClearDataSource: TaskClearDataSource by dependencies
                taskClearDataSource.clear()
            }

            testingScenario("タスク作成ができる") {
                whenDo {
                    path = "/api/v1/task/create"
                    method = HttpMethod.Post
                    body =
                        CreateTaskDummyRequest(
                            title = "test1",
                            description = "test2",
                        )
                }
                then {
                    assertEquals(HttpStatusCode.Created, response.status)

                    val body = runBlocking { response.body<CreateTaskResponse>() }
                    assertTrue(
                        Pattern.matches(
                            "([0-9a-f]{8})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{12})",
                            body.id.toString(),
                        ),
                    )
                }
            }

            testingScenario("タイトルが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/create"
                    method = HttpMethod.Post
                    body =
                        CreateTaskDummyRequest(
                            description = "test2",
                        )
                }
                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タイトルが空の場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/create"
                    method = HttpMethod.Post
                    body =
                        CreateTaskDummyRequest(
                            title = "",
                            description = "test2",
                        )
                }
                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タイトルが200文字を超える場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/create"
                    method = HttpMethod.Post
                    body =
                        CreateTaskDummyRequest(
                            title = "t".repeat(201),
                            description = "test2",
                        )
                }
                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }
        }
}
