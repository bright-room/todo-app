package net.brightroom.todo.backend.api.application.repository

import net.brightroom.todo.shared.domain.model.Task

/**
 * タスク作成リポジトリ
 */
interface TaskCreateRepository {
    /**
     * タスクを作成する
     * @param task 作成するタスク
     * @return 作成されたタスク（IDが設定済み）
     */
    suspend fun create(task: Task): Task
}
