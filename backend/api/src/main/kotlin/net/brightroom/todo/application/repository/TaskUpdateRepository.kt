package net.brightroom.todo.application.repository

import net.brightroom.todo.domain.model.task.Task

/**
 * タスク更新リポジトリ
 */
interface TaskUpdateRepository {
    /**
     * タスクを更新する
     * @param task 更新するタスク
     * @return 更新されたタスク
     */
    suspend fun update(task: Task): Task
}
