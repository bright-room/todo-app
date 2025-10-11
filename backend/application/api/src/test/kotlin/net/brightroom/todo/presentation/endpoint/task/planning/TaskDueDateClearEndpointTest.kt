package net.brightroom.todo.presentation.endpoint.task.planning

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.di.dependencies
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import net.brightroom._extensions.kotlinx.datetime.now
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
private data class TaskDueDateClearDummyRequest(
    val id: String? = null,
)

@DisplayName("タスク期限日削除のE2Eテスト")
class TaskDueDateClearEndpointTest {
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
                                id = Id(Uuid.parse("eb6146f2-96e8-04d7-56ea-7646c55aee1b")),
                                createdTime = CreatedTime(LocalDateTime.parse("2023-09-01T00:00:00")),
                            ),
                        content =
                            Content(
                                title = Title("test"),
                                description = Description("test"),
                            ),
                        planning =
                            Planning(
                                dueDate = DueDateFactory.create(LocalDate.now()),
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
                                id = Id(Uuid.parse("986e4e63-526c-1d1c-7877-2d40233a79c0")),
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

            testingScenario("タスクの期限日を削除できる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/clear"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateClearDummyRequest(
                            id = "eb6146f2-96e8-04d7-56ea-7646c55aee1b",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.OK, response.status)
                }
            }

            testingScenario("タスクが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/clear"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateClearDummyRequest(
                            id = "eb6146f2-96e8-04d7-56ea-7646c55aee1c",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.NotFound, response.status)
                }
            }

            testingScenario("タスクIDが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/clear"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateClearDummyRequest()
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タスクIDが空の場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/clear"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateClearDummyRequest(
                            id = "",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("期限日が設定されていない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/clear"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateClearDummyRequest(
                            id = "986e4e63-526c-1d1c-7877-2d40233a79c0",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }
        }
}
