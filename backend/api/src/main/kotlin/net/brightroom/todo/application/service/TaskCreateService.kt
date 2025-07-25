package net.brightroom.todo.application.service

import net.brightroom.todo.backend.api.application.repository.TaskCreateRepository
import net.brightroom.todo.domain.model.task.Description
import net.brightroom.todo.domain.model.task.duedate.SetDueDate
import net.brightroom.todo.domain.model.task.Task
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.Title

/**
 * タスク作成サービス
 */
class TaskCreateService(
    private val taskCreateRepository: TaskCreateRepository,
) {
    /**
     * 新しいタスクを作成する
     */
    suspend fun create(
        title: String,
        description: String = "",
        dueDate: String,
    ): Task {
        // ドメインオブジェクトの生成
        val task =
            Task(
                id = TaskId.generate(),
                title = Title(title),
                description = Description(description),
                dueDate = SetDueDate.parse(dueDate),
                isCompleted = false,
            )

        // リポジトリを通じた永続化
        return taskCreateRepository.create(task)
    }

    /**
     * タスクオブジェクトを直接作成する
     */
    suspend fun create(task: Task): Task = taskCreateRepository.create(task)
}
