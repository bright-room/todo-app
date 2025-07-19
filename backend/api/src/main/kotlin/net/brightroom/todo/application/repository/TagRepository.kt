package net.brightroom.todo.application.repository

import net.brightroom.todo.domain.model.Tag
import net.brightroom.todo.domain.model.TagId
import net.brightroom.todo.domain.model.TaskId

interface TagRepository {
    suspend fun findById(id: TagId): Tag?
    suspend fun findAll(): List<Tag>
    suspend fun findByTitle(title: String): Tag?
    suspend fun findByTaskId(taskId: TaskId): List<Tag>
    suspend fun save(tag: Tag): Tag
    suspend fun update(tag: Tag): Tag
    suspend fun delete(id: TagId): Boolean
    suspend fun existsById(id: TagId): Boolean
    suspend fun existsByTitle(title: String): Boolean
    suspend fun addTagToTask(tagId: TagId, taskId: TaskId): Boolean
    suspend fun removeTagFromTask(tagId: TagId, taskId: TaskId): Boolean
}