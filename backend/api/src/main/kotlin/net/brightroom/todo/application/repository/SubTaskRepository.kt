package net.brightroom.todo.application.repository

import net.brightroom.todo.domain.model.SubTask
import net.brightroom.todo.domain.model.SubTaskId
import net.brightroom.todo.domain.model.TaskId

interface SubTaskRepository {
    suspend fun findById(id: SubTaskId): SubTask?
    suspend fun findByTaskId(taskId: TaskId): List<SubTask>
    suspend fun findByCompleted(completed: Boolean): List<SubTask>
    suspend fun save(subTask: SubTask): SubTask
    suspend fun update(subTask: SubTask): SubTask
    suspend fun delete(id: SubTaskId): Boolean
    suspend fun deleteByTaskId(taskId: TaskId): Boolean
    suspend fun existsById(id: SubTaskId): Boolean
}