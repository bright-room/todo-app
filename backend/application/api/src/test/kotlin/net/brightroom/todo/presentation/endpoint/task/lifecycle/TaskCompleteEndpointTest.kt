package net.brightroom.todo.presentation.endpoint.task.lifecycle

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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestFactory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
private data class TaskCompleteDummyRequest(
    val id: String? = null,
)

@DisplayName("未完了のタスクを完了済みに遷移させるE2Eテスト")
class TaskCompleteEndpointTest {
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
                                id = Id(Uuid.parse("e8d47326-1dd0-f9c5-0960-a78397187e6d")),
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
                    Task(
                        identity =
                            Identity(
                                id = Id(Uuid.parse("301b13df-5cd1-04d4-00b7-b93742a2e009")),
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
                                status = Status.完了,
                                completedTime = CompletedTimeFactory.create(LocalDateTime.parse("2023-09-01T00:00:00")),
                            ),
                    ),
                )
            }

            finalize {
                val taskClearDataSource: TaskClearDataSource by dependencies
                taskClearDataSource.clear()
            }

            testingScenario("タスクの完了済みにできる") {
                whenDo {
                    path = "/api/v1/task/lifecycle/complete"
                    method = HttpMethod.Post
                    body =
                        TaskCompleteDummyRequest(
                            id = "e8d47326-1dd0-f9c5-0960-a78397187e6d",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.OK, response.status)
                }
            }

            testingScenario("タスクが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/lifecycle/complete"
                    method = HttpMethod.Post
                    body =
                        TaskCompleteDummyRequest(
                            id = "e8d47326-1dd0-f9c5-0960-a78397187e6e",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.NotFound, response.status)
                }
            }

            testingScenario("タスクIDが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/lifecycle/complete"
                    method = HttpMethod.Post
                    body =
                        TaskCompleteDummyRequest()
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タスクIDが空の場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/lifecycle/complete"
                    method = HttpMethod.Post
                    body =
                        TaskCompleteDummyRequest(
                            id = "",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タスクが既に完了済みの場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/lifecycle/complete"
                    method = HttpMethod.Post
                    body =
                        TaskCompleteDummyRequest(
                            id = "301b13df-5cd1-04d4-00b7-b93742a2e009",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.Conflict, response.status)
                }
            }
        }
}
