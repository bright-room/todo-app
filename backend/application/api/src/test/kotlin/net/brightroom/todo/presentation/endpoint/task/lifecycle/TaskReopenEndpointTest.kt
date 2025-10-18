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
private data class TaskReopenDummyRequest(val id: String? = null)

@DisplayName("未完了のタスクを再オープンに遷移させるE2Eテスト")
class TaskReopenEndpointTest {
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
                                id = Id(Uuid.parse("0b7983cb-3d1a-10d5-b79d-7204353a5522")),
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
                                id = Id(Uuid.parse("df276bbe-38c4-2291-1f20-c3227a3a8a5f")),
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

            testingScenario("タスクを再オープンできる") {
                whenDo {
                    path = "/api/v1/task/lifecycle/reopen"
                    method = HttpMethod.Post
                    body =
                        TaskReopenDummyRequest(
                            id = "df276bbe-38c4-2291-1f20-c3227a3a8a5f",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.OK, response.status)
                }
            }

            testingScenario("タスクが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/lifecycle/reopen"
                    method = HttpMethod.Post
                    body =
                        TaskReopenDummyRequest(
                            id = "df276bbe-38c4-2291-1f20-c3227a3a8a60",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.NotFound, response.status)
                }
            }

            testingScenario("タスクIDが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/lifecycle/reopen"
                    method = HttpMethod.Post
                    body =
                        TaskReopenDummyRequest()
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タスクIDが空の場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/lifecycle/reopen"
                    method = HttpMethod.Post
                    body =
                        TaskReopenDummyRequest(
                            id = "",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タスクが既にオープンの場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/lifecycle/reopen"
                    method = HttpMethod.Post
                    body =
                        TaskReopenDummyRequest(
                            id = "0b7983cb-3d1a-10d5-b79d-7204353a5522",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.Conflict, response.status)
                }
            }
        }
}
