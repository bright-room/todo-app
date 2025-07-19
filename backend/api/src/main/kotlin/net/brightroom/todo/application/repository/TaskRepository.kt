package net.brightroom.todo.application.repository

import net.brightroom.todo.domain.model.Task
import net.brightroom.todo.domain.model.TaskId

interface TaskRepository {
    suspend fun findById(id: TaskId): Task?
    suspend fun findAll(): List<Task>
    suspend fun findByCompleted(completed: Boolean): List<Task>
    suspend fun save(task: Task): Task
    suspend fun update(task: Task): Task
    suspend fun delete(id: TaskId): Boolean
    suspend fun existsById(id: TaskId): Boolean
}