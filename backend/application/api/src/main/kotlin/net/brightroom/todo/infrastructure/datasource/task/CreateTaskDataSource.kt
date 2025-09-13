@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.infrastructure.datasource.task

import net.brightroom.schemas.schema.todo.TaskIdTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.application.repository.task.CreateTaskRepository
import net.brightroom.todo.domain.model.identity.CreatedTime
import net.brightroom.todo.domain.model.identity.Id
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insertAndGetId
import kotlin.uuid.ExperimentalUuidApi

class CreateTaskDataSource(
    private val db: R2dbcDatabase,
) : CreateTaskRepository {
    override suspend fun create(): Id =
        transaction(db) {
            val createdTime = CreatedTime.now()
            val id =
                TaskIdTable.insertAndGetId {
                    it[TaskIdTable.created_at] = createdTime()
                }

            Id(id.value)
        }
}
