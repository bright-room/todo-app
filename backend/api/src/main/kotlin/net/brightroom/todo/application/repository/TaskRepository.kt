package net.brightroom.todo.application.repository

import net.brightroom.todo.domain.model.task.Task
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.Tasks

/**
 * タスク参照リポジトリ
 */
interface TaskRepository {
    /**
     * 指定されたIDのタスクを取得する
     * @param taskId タスクID
     * @return タスクオブジェクト
     * @throws net.brightroom.todo.domain.problem.ResourceNotFoundException タスクが見つからない場合
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
