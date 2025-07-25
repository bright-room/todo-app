package net.brightroom.todo.application.service

import net.brightroom.todo.backend.api.application.repository.TaskRepository
import net.brightroom.todo.domain.model.task.Task
import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.Tasks

/**
 * タスク参照サービス
 */
class TaskQueryService(
    private val taskRepository: TaskRepository,
) {
    /**
     * 指定されたIDのタスクを取得する
     */
    suspend fun find(taskId: TaskId): Task = taskRepository.find(taskId)

    /**
     * 全てのタスクを取得する
     */
    suspend fun listAll(): Tasks = taskRepository.listAll()

    /**
     * 完了済みのタスクを取得する
     */
    suspend fun findCompleted(): Tasks = taskRepository.findCompleted()

    /**
     * 未完了のタスクを取得する
     */
    suspend fun findIncomplete(): Tasks = taskRepository.findIncomplete()

    /**
     * 期限切れのタスクを取得する
     */
    suspend fun findOverdue(): Tasks = taskRepository.findOverdue()
}
