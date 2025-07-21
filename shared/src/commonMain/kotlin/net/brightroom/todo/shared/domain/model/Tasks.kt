package net.brightroom.todo.shared.domain.model

import kotlinx.serialization.Serializable

/**
 * タスクコレクション
 */
@Serializable
data class Tasks(
    val list: List<Task>,
) {
    /**
     * 期限切れのタスクを取得する
     */
    fun getOverdueTasks(): Tasks = Tasks(list.filter { it.isOverdue() })

    /**
     * 完了済みのタスクを取得する
     */
    fun getCompletedTasks(): Tasks = Tasks(list.filter { it.isCompleted })

    /**
     * 未完了のタスクを取得する
     */
    fun getIncompleteTasks(): Tasks = Tasks(list.filter { !it.isCompleted })

    /**
     * タスクの総数を取得する
     */
    fun count(): Int = list.size

    /**
     * 完了済みタスクの数を取得する
     */
    fun completedCount(): Int = list.count { it.isCompleted }

    /**
     * 未完了タスクの数を取得する
     */
    fun incompleteCount(): Int = list.count { !it.isCompleted }

    /**
     * 期限切れタスクの数を取得する
     */
    fun overdueCount(): Int = list.count { it.isOverdue() }

    /**
     * 指定されたIDのタスクを検索する
     */
    fun findById(taskId: TaskId): Task? = list.find { it.id == taskId }

    /**
     * タスクが空かどうかを判定する
     */
    fun isEmpty(): Boolean = list.isEmpty()

    /**
     * タスクが存在するかどうかを判定する
     */
    fun isNotEmpty(): Boolean = list.isNotEmpty()

    companion object {
        fun empty(): Tasks = Tasks(emptyList())
    }
}
