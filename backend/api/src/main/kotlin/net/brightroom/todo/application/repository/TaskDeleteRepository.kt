package net.brightroom.todo.application.repository

import net.brightroom.todo.domain.model.task.TaskId

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
