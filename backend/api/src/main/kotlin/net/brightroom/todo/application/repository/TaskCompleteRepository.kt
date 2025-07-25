package net.brightroom.todo.application.repository

import net.brightroom.todo.domain.model.task.TaskId

/**
 * タスク完了リポジトリ
 */
interface TaskCompleteRepository {
    /**
     * タスクを完了状態にする
     * @param taskId 完了するタスクのID
     */
    suspend fun complete(taskId: TaskId)

    /**
     * タスクを未完了状態にする
     * @param taskId 未完了にするタスクのID
     */
    suspend fun uncomplete(taskId: TaskId)
}
