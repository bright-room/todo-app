package net.brightroom.todo.backend.api.application.service

import net.brightroom.todo.backend.api.application.repository.TaskCompleteRepository
import net.brightroom.todo.backend.api.application.repository.TaskRepository
import net.brightroom.todo.shared.domain.model.TaskId

/**
 * タスク完了サービス
 */
class TaskCompleteService(
    private val taskRepository: TaskRepository,
    private val taskCompleteRepository: TaskCompleteRepository,
) {
    /**
     * タスクを完了状態にする
     */
    suspend fun complete(taskId: TaskId) {
        // 存在チェック
        taskRepository.find(taskId) // 存在しない場合は例外がスローされる

        // 完了処理
        taskCompleteRepository.complete(taskId)
    }

    /**
     * タスクを未完了状態にする
     */
    suspend fun uncomplete(taskId: TaskId) {
        // 存在チェック
        taskRepository.find(taskId) // 存在しない場合は例外がスローされる

        // 未完了処理
        taskCompleteRepository.uncomplete(taskId)
    }
}
