package net.brightroom.todo.application.service

import net.brightroom.todo.backend.api.application.repository.TaskRepository
import net.brightroom.todo.backend.api.application.repository.TaskUpdateRepository
import net.brightroom.todo.domain.model.task.Description
import net.brightroom.todo.domain.model.task.duedate.SetDueDate
import net.brightroom.todo.domain.model.task.Task
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.Title

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
                dueDate = dueDate?.let { SetDueDate.parse(it) },
            )

        // リポジトリを通じた更新
        return taskUpdateRepository.update(updatedTask)
    }

    /**
     * タスクオブジェクトを直接更新する
     */
    suspend fun update(task: Task): Task = taskUpdateRepository.update(task)
}
