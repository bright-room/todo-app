package net.brightroom.todo.presentation.endpoint.task

import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.di.dependencies
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
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
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@DisplayName("タスク取得のE2Eテスト")
class TaskEndpointTest {
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
                                id = Id(Uuid.parse("aa07c76b-eb95-cdfc-c1fe-2687c1083282")),
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
                )
            }

            finalize {
                val taskClearDataSource: TaskClearDataSource by dependencies
                taskClearDataSource.clear()
            }

            testingScenario("IDに紐づくタスクが取得できる") {
                whenDo {
                    path = "/api/v1/task"
                    method = HttpMethod.Get
                    queryParam("id", "aa07c76b-eb95-cdfc-c1fe-2687c1083282")
                }

                then {
                    assertEquals(HttpStatusCode.OK, response.status)

                    val body = runBlocking { response.body<Task>() }
                    assertEquals(
                        "aa07c76b-eb95-cdfc-c1fe-2687c1083282",
                        body.identity.id.toString(),
                    )
                }
            }

            testingScenario("IDに紐づくタスクが存在しない場合エラーになる") {
                whenDo {
                    path = "/api/v1/task"
                    method = HttpMethod.Get
                    queryParam("id", "aa07c76b-eb95-cdfc-c1fe-2687c1083283")
                }

                then {
                    assertEquals(HttpStatusCode.NotFound, response.status)
                }
            }

            testingScenario("IDがクエリに含まれていない場合エラーになる") {
                whenDo {
                    path = "/api/v1/task"
                    method = HttpMethod.Get
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }

            testingScenario("IDの形式が不正の場合エラーになる") {
                whenDo {
                    path = "/api/v1/task"
                    method = HttpMethod.Get
                    queryParam("id", "aa07c76b-eb95-cdfc-c1fe-2687c1083282a")
                }

                then {
                    assertEquals(HttpStatusCode.BadRequest, response.status)
                }
            }
        }
}
