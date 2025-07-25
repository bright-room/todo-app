package net.brightroom.todo.application.repository.task

import net.brightroom.todo.domain.model.task.content.Content

interface TaskCreateRepository {
    fun create(content: Content)
}
