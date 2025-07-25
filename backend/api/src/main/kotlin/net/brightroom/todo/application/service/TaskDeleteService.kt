package net.brightroom.todo.application.service

import net.brightroom.todo.backend.api.application.repository.TaskDeleteRepository
import net.brightroom.todo.backend.api.application.repository.TaskRepository
import net.brightroom.todo.domain.model.task.TaskId

/**
 * タスク削除サービス
 */
class TaskDeleteService(
    private val taskRepository: TaskRepository,
    private val taskDeleteRepository: TaskDeleteRepository,
) {
    /**
     * タスクを削除する
     */
    suspend fun delete(taskId: TaskId) {
        // 存在チェック
        taskRepository.find(taskId) // 存在しない場合は例外がスローされる

        // 削除処理
        taskDeleteRepository.delete(taskId)
    }
}
