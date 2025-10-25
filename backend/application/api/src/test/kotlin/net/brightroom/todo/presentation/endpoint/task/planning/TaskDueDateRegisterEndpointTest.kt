package net.brightroom.todo.presentation.endpoint.task.planning

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
private data class TaskDueDateRegisterDummyRequest(val id: String? = null, val dueDate: String? = null)

@DisplayName("タスク期限日登録のE2Eテスト")
class TaskDueDateRegisterEndpointTest {
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
                                id = Id(Uuid.parse("5b90b962-cdf0-b16a-754b-b50977a97938")),
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

            testingScenario("タスクの期限日を登録できる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/register"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateRegisterDummyRequest(
                            id = "5b90b962-cdf0-b16a-754b-b50977a97938",
                            dueDate = "2025-09-27",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.OK, response.status)
                }
            }

            testingScenario("タスクが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/register"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateRegisterDummyRequest(
                            id = "5b90b962-cdf0-b16a-754b-b50977a97939",
                            dueDate = "2025-09-27",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.NotFound, response.status)
                }
            }

            testingScenario("タスクIDが存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/register"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateRegisterDummyRequest(
                            dueDate = "2025-09-27",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("タスクIDが空の場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/register"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateRegisterDummyRequest(
                            id = "",
                            dueDate = "2025-09-27",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("期限日が存在しない場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/register"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateRegisterDummyRequest(
                            id = "5b90b962-cdf0-b16a-754b-b50977a97938",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("期限日が空の場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/register"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateRegisterDummyRequest(
                            id = "5b90b962-cdf0-b16a-754b-b50977a97938",
                            dueDate = "",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("期限日の形式が異なる場合エラーとなる") {
                whenDo {
                    path = "/api/v1/task/planning/due-date/register"
                    method = HttpMethod.Post
                    body =
                        TaskDueDateRegisterDummyRequest(
                            id = "5b90b962-cdf0-b16a-754b-b50977a97938",
                            dueDate = "2525/08/27",
                        )
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }
        }
}
