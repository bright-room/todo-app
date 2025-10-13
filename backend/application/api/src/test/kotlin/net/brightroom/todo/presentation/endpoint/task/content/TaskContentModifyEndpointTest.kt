package net.brightroom.todo.presentation.endpoint.task.content

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.di.dependencies
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.Task
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.content.Description
import net.brightroom.todo.domain.model.content.Title
import net.brightroom.todo.domain.model.identity.CreatedTime
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.identity.Identity
import net.brightroom.todo.domain.model.lifecycle.Lifecycle
import net.brightroom.todo.domain.model.lifecycle.Status
import net.brightroom.todo.domain.model.lifecycle.complete.CompletedTimeFactory
import net.brightroom.todo.domain.model.planning.Planning
import net.brightroom.todo.domain.model.planning.Priority
import net.brightroom.todo.domain.model.planning.due.DueDateFactory
import net.brightroom.todo.infrastructure.datasource.task.CreateDummyTaskDataSource
import net.brightroom.todo.infrastructure.datasource.task.TaskClearDataSource
import net.brightroom.todo.presentation.endpoint.endToEndTestApplication
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
private data class DummyContent(val title: String? = null, val description: String? = null)

@Serializable
private data class TaskContentModifyDummyRequest(val id: String? = null, val content: DummyContent? = null)

@DisplayName("タスクコンテンの編集のE2Eテスト")
class TaskContentModifyEndpointTest {
    @OptIn(ExperimentalUuidApi::class)
    @Tag("integration")
    @TestFactory
    fun run() =
        endToEndTestApplication {
            initialize {
                val createDummyTaskDataSource: CreateDummyTaskDataSource by dependencies

                createDummyTaskDataSource.create(
                    Task(
                        identity =
                            Identity(
                                id = Id(Uuid.parse("4f40dbe1-03e3-8fd0-013d-3cb8a96b9248")),
                                createdTime = CreatedTime(LocalDateTime.parse("2023-09-01T00:00:00")),
                            ),
                        content =
                            Content(
                                title = Title("test"),
                                description = Description("test"),
                            ),
                        planning =
                            Planning(
                                dueDate = DueDateFactory.create(null),
                                priority = Priority.Low,
                            ),
                        lifecycle =
                            Lifecycle(
                                status = Status.未完了,
                                completedTime = CompletedTimeFactory.create(null),
                            ),
                    ),
                )
            }

            finalize {
                val taskClearDataSource: TaskClearDataSource by dependencies
                taskClearDataSource.clear()
            }

            testingScenario("タスクコンテンツを編集できる") {
                whenDo {
                    path = "/api/v1/task/content/modify"
                    method = HttpMethod.Post
                    body =
                        TaskContentModifyDummyRequest(
                            id = "4f40dbe1-03e3-8fd0-013d-3cb8a96b9248",
                            content =
                                DummyContent(
                                    title = "タイトル変更",
                                    description = "test",
                                ),
                        )
                }

                then {
                    assertEquals(HttpStatusCode.OK, response.status)
                }
            }

            testingScenario("タスクが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/content/modify"
                    method = HttpMethod.Post
                    body =
                        TaskContentModifyDummyRequest(
                            id = "4f40dbe1-03e3-8fd0-013d-3cb8a96b9249",
                            content =
                                DummyContent(
                                    title = "タイトル変更",
                                    description = "test",
                                ),
                        )
                }

                then {
                    assertEquals(HttpStatusCode.NotFound, response.status)
                }
            }

            testingScenario("タスクIDが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/content/modify"
                    method = HttpMethod.Post
                    body =
                        TaskContentModifyDummyRequest(
                            content =
                                DummyContent(
                                    title = "タイトル変更",
                                    description = "test",
                                ),
                        )
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タスクIDが空の場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/content/modify"
                    method = HttpMethod.Post
                    body =
                        TaskContentModifyDummyRequest(
                            id = "",
                            content =
                                DummyContent(
                                    title = "タイトル変更",
                                    description = "test",
                                ),
                        )
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タイトルが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/content/modify"
                    method = HttpMethod.Post
                    body =
                        TaskContentModifyDummyRequest(
                            id = "4f40dbe1-03e3-8fd0-013d-3cb8a96b9248",
                            content =
                                DummyContent(
                                    description = "test",
                                ),
                        )
                }
                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タイトルが空の場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/content/modify"
                    method = HttpMethod.Post
                    body =
                        TaskContentModifyDummyRequest(
                            id = "4f40dbe1-03e3-8fd0-013d-3cb8a96b9248",
                            content =
                                DummyContent(
                                    title = "",
                                    description = "test2",
                                ),
                        )
                }
                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タイトルが200文字を超える場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/content/modify"
                    method = HttpMethod.Post
                    body =
                        TaskContentModifyDummyRequest(
                            id = "4f40dbe1-03e3-8fd0-013d-3cb8a96b9248",
                            content =
                                DummyContent(
                                    title = "t".repeat(201),
                                    description = "test2",
                                ),
                        )
                }
                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }
        }
}
