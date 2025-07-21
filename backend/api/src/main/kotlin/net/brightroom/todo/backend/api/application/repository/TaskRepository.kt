package net.brightroom.todo.backend.api.application.repository

import net.brightroom.todo.shared.domain.model.Task
import net.brightroom.todo.shared.domain.model.TaskId
import net.brightroom.todo.shared.domain.model.Tasks

/**
 * タスク参照リポジトリ
 */
interface TaskRepository {
    /**
     * 指定されたIDのタスクを取得する
     * @param taskId タスクID
     * @return タスクオブジェクト
     * @throws net.brightroom.todo.shared.domain.problem.TaskNotFoundException タスクが見つからない場合
     */
    suspend fun find(taskId: TaskId): Task

    /**
     * 全てのタスクを取得する
     * @return タスク一覧
     */
    suspend fun listAll(): Tasks

    /**
     * 完了済みのタスクを取得する
     * @return 完了済みタスク一覧
     */
    suspend fun findCompleted(): Tasks

    /**
     * 未完了のタスクを取得する
     * @return 未完了タスク一覧
     */
    suspend fun findIncomplete(): Tasks

    /**
     * 期限切れのタスクを取得する
     * @return 期限切れタスク一覧
     */
    suspend fun findOverdue(): Tasks
}
