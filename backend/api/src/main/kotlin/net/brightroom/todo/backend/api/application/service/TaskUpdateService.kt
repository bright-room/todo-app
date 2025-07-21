package net.brightroom.todo.backend.api.application.service

import net.brightroom.todo.backend.api.application.repository.TaskRepository
import net.brightroom.todo.backend.api.application.repository.TaskUpdateRepository
import net.brightroom.todo.shared.domain.model.Description
import net.brightroom.todo.shared.domain.model.DueDate
import net.brightroom.todo.shared.domain.model.Task
import net.brightroom.todo.shared.domain.model.TaskId
import net.brightroom.todo.shared.domain.model.Title

/**
 * タスク更新サービス
 */
class TaskUpdateService(
    private val taskRepository: TaskRepository,
    private val taskUpdateRepository: TaskUpdateRepository,
) {
    /**
     * タスクの内容を更新する
     */
    suspend fun update(
        taskId: TaskId,
        title: String? = null,
        description: String? = null,
        dueDate: String? = null,
    ): Task {
        // 既存タスクの取得
        val existingTask = taskRepository.find(taskId)

        // 更新されたタスクの作成
        val updatedTask =
            existingTask.update(
                title = title?.let { Title(it) },
                description = description?.let { Description(it) },
                dueDate = dueDate?.let { DueDate.parse(it) },
            )

        // リポジトリを通じた更新
        return taskUpdateRepository.update(updatedTask)
    }

    /**
     * タスクオブジェクトを直接更新する
     */
    suspend fun update(task: Task): Task = taskUpdateRepository.update(task)
}
