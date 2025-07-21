package net.brightroom.todo.backend.api.application.repository

import net.brightroom.todo.shared.domain.model.TaskId

/**
 * タスク削除リポジトリ
 */
interface TaskDeleteRepository {
    /**
     * タスクを削除する
     * @param taskId 削除するタスクのID
     */
    suspend fun delete(taskId: TaskId)
}
