package net.brightroom.todo.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import net.brightroom.todo.domain.model.task.*
import net.brightroom.todo.domain.model.task.content.*

/**
 * バックエンドAPIとの通信を行うサービスクラス
 */
class TaskApiService {
    private val baseUrl = "http://localhost:8081"
    
    @OptIn(ExperimentalSerializationApi::class)
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                encodeDefaults = true
                classDiscriminatorMode = ClassDiscriminatorMode.NONE
                namingStrategy = JsonNamingStrategy.SnakeCase
            })
        }
    }

    /**
     * 全てのタスクを取得
     */
    suspend fun getAllTasks(): Tasks {
        return httpClient.get("$baseUrl/v1/task/list").body()
    }

    /**
     * 指定されたIDのタスクを取得
     */
    suspend fun getTask(id: TaskId): Task {
        return httpClient.get("$baseUrl/v1/task") {
            parameter("id", id.toString())
        }.body()
    }

    /**
     * 新しいタスクを作成
     */
    suspend fun createTask(title: Title, description: Description) {
        httpClient.post("$baseUrl/v1/task/create") {
            contentType(ContentType.Application.Json)
            setBody(TaskCreateRequest(title, description))
        }
    }

    /**
     * タスクの内容を更新
     */
    suspend fun updateTaskContent(id: TaskId, content: Content) {
        httpClient.post("$baseUrl/v1/task/modify") {
            contentType(ContentType.Application.Json)
            setBody(TaskContentModifyRequest(id, content))
        }
    }

    /**
     * タスクを完了状態にする
     */
    suspend fun completeTask(id: TaskId) {
        httpClient.post("$baseUrl/v1/task/complete") {
            contentType(ContentType.Application.Json)
            setBody(TaskCompleteRequest(id))
        }
    }

    /**
     * タスクを未完了状態に戻す
     */
    suspend fun revertTaskCompletion(id: TaskId) {
        httpClient.post("$baseUrl/v1/task/revert-to-completion") {
            contentType(ContentType.Application.Json)
            setBody(TaskRevertCompletionRequest(id))
        }
    }

    /**
     * リソースのクリーンアップ
     */
    fun close() {
        httpClient.close()
    }
}

// リクエストデータクラス（バックエンドのリクエスト構造に合わせる）
@kotlinx.serialization.Serializable
data class TaskCreateRequest(
    val title: Title,
    val description: Description = Description(""),
)

@kotlinx.serialization.Serializable
data class TaskCompleteRequest(
    val id: TaskId,
)

@kotlinx.serialization.Serializable
data class TaskRevertCompletionRequest(
    val id: TaskId,
)

@kotlinx.serialization.Serializable
data class TaskContentModifyRequest(
    val id: TaskId,
    val content: Content,
)