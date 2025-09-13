@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.infrastructure.datasource.task.content

import net.brightroom.schemas.schema.todo.TaskContentTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.application.repository.task.content.TaskContentRegisterRepository
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insert
import kotlin.uuid.ExperimentalUuidApi

class TaskContentRegisterDataSource(
    private val db: R2dbcDatabase,
) : TaskContentRegisterRepository {
    override suspend fun register(
        content: Content,
        id: Id,
    ): Unit =
        transaction(db) {
            val createdTime = CreatedTime.now()
            TaskContentTable.insert {
                it[TaskContentTable.task_id] = id()
                it[TaskContentTable.title] = content.title()
                it[TaskContentTable.description] = content.description()
                it[TaskContentTable.created_at] = createdTime()
            }
        }
}
