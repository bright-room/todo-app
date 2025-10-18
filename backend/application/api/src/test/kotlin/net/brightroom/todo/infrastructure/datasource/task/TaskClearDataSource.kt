package net.brightroom.todo.infrastructure.datasource.task

import net.brightroom.schemas.schema.todo.TaskIdTable
import net.brightroom.schemas.transaction.transaction
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.deleteAll

class TaskClearDataSource(private val db: R2dbcDatabase) {
    suspend fun clear() =
        transaction(db) {
            TaskIdTable.deleteAll()
        }
}
