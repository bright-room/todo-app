package net.brightroom.todo.presentation.endpoint.task

import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.di.dependencies
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import net.brightroom._extensions.kotlinx.datetime.now
import net.brightroom.todo.domain.model.Task
import net.brightroom.todo.domain.model.Tasks
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
import kotlin.test.assertTrue
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@DisplayName("タスク一覧取得のE2Eテスト")
class TaskListEndpointTest {
    @OptIn(ExperimentalUuidApi::class)
    @Tag("integration")
    @TestFactory
    fun run() =
        endToEndTestApplication {
            testingScenario("タスクの一覧が取得できる") {
                given {
                    setup {
                        val createDummyTaskDataSource: CreateDummyTaskDataSource by dependencies
                        createDummyTaskDataSource.create(
                            Task(
                                identity =
                                    Identity(
                                        id = Id(Uuid.parse("57cddea7-4cbc-638d-bde7-688b47e0bf26")),
                                        createdTime = CreatedTime(LocalDateTime.now()),
                                    ),
                                content =
                                    Content(
                                        title = Title("テストタスク1"),
                                        description = Description("これはテストタスクです。"),
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
                                        id = Id(Uuid.parse("5d5f2127-e1b1-c3ae-02d0-daee29bd7c4f")),
                                        createdTime = CreatedTime(LocalDateTime.now()),
                                    ),
                                content =
                                    Content(
                                        title = Title("テストタスク2"),
                                        description = Description("これはテストタスクです。"),
                                    ),
                                planning =
                                    Planning(
                                        dueDate = DueDateFactory.create(LocalDate.now()),
                                        priority = Priority.Critical,
                                    ),
                                lifecycle =
                                    Lifecycle(
                                        status = Status.完了,
                                        completedTime = CompletedTimeFactory.create(LocalDateTime.now()),
                                    ),
                            ),
                        )
                    }

                    tearDown {
                        val taskClearDataSource: TaskClearDataSource by dependencies
                        taskClearDataSource.clear()
                    }
                }

                whenDo {
                    path = "/api/v1/task/list"
                    method = HttpMethod.Get
                }

                then {
                    assertEquals(HttpStatusCode.OK, response.status)

                    val body = runBlocking { response.body<Tasks>() }
                    assertEquals(2, body.list.size)
                }
            }

            testingScenario("タスクが存在しない場合空のタスク一覧が取得できる") {
                whenDo {
                    path = "/api/v1/task/list"
                    method = HttpMethod.Get
                }

                then {
                    assertEquals(HttpStatusCode.OK, response.status)

                    val body = runBlocking { response.body<Tasks>() }
                    assertTrue(body.list.isEmpty())
                }
            }
        }
}
