@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.infrastructure.datasource.task

import net.brightroom.schemas.schema.todo.TaskIdTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.application.repository.task.CreateTaskRegisterRepository
import net.brightroom.todo.domain.model.identity.CreatedTime
import net.brightroom.todo.domain.model.identity.Id
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insertAndGetId
import kotlin.uuid.ExperimentalUuidApi

class CreateTaskRegisterDataSource(
    private val db: R2dbcDatabase,
) : CreateTaskRegisterRepository {
    override suspend fun create(): Id =
        transaction(db) {
            val createdTime = CreatedTime.Companion.now()
            val id =
                TaskIdTable.insertAndGetId {
                    it[TaskIdTable.created_at] = createdTime()
                }

            Id(id.value)
        }
}
