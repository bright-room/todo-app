@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.infrastructure.datasource.task.planning

import net.brightroom.schemas.schema.todo.TaskPriorityTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.application.repository.task.planning.TaskPriorityRegisterRepository
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.Priority
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insert
import kotlin.uuid.ExperimentalUuidApi

class TaskPriorityRegisterDataSource(private val db: R2dbcDatabase) : TaskPriorityRegisterRepository {
    override suspend fun register(
        priority: Priority,
        id: Id,
    ): Unit =
        transaction(db) {
            val ceratedTime = CreatedTime.now()
            TaskPriorityTable.insert {
                it[TaskPriorityTable.task_id] = id()
                it[TaskPriorityTable.priority] = priority
                it[TaskPriorityTable.created_at] = ceratedTime()
            }
        }
}
