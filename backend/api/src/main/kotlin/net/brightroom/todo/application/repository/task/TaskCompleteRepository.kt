package net.brightroom.todo.application.repository.task

import net.brightroom.todo.domain.model.task.TaskId
import org.jetbrains.exposed.v1.core.statements.InsertStatement

interface TaskCompleteRepository {
    suspend fun complete(id: TaskId)
    suspend fun revertCompletion(id: TaskId)
}