package net.brightroom.todo.backend.api.application.service

import net.brightroom.todo.backend.api.application.repository.TaskCreateRepository
import net.brightroom.todo.shared.domain.model.Description
import net.brightroom.todo.shared.domain.model.DueDate
import net.brightroom.todo.shared.domain.model.Task
import net.brightroom.todo.shared.domain.model.TaskId
import net.brightroom.todo.shared.domain.model.Title

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
                dueDate = DueDate.parse(dueDate),
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
