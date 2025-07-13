package net.brightroom.todo.application.repository

import net.brightroom.todo.domain.model.TaskGroup
import net.brightroom.todo.domain.model.TaskGroupId
import net.brightroom.todo.domain.model.TaskId

interface TaskGroupRepository {
    suspend fun findById(id: TaskGroupId): TaskGroup?
    suspend fun findAll(): List<TaskGroup>
    suspend fun findByTaskId(taskId: TaskId): List<TaskGroup>
    suspend fun save(taskGroup: TaskGroup): TaskGroup
    suspend fun update(taskGroup: TaskGroup): TaskGroup
    suspend fun delete(id: TaskGroupId): Boolean
    suspend fun existsById(id: TaskGroupId): Boolean
    suspend fun addTaskToGroup(taskGroupId: TaskGroupId, taskId: TaskId): Boolean
    suspend fun removeTaskFromGroup(taskGroupId: TaskGroupId, taskId: TaskId): Boolean
}