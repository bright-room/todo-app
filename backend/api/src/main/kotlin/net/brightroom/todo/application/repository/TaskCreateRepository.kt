package net.brightroom.todo.application.repository

import net.brightroom.todo.domain.model.task.Task

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
