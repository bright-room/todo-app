@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.infrastructure.datasource.task.planning

import net.brightroom.schemas.schema.todo.TaskDueDateTable
import net.brightroom.schemas.schema.todo.TaskPriorityTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.application.repository.task.planning.TaskPlanningRegisterRepository
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.domain.model.planning.Planning
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insert
import kotlin.uuid.ExperimentalUuidApi

class TaskPlanningRegisterDataSource(
    private val db: R2dbcDatabase,
) : TaskPlanningRegisterRepository {
    override suspend fun register(
        planning: Planning,
        id: Id,
    ): Unit =
        transaction(db) {
            val ceratedTime = CreatedTime.now()

            val dueDate = planning.dueDate
            if (dueDate.is期限日がセット済み()) {
                TaskDueDateTable.insert {
                    it[TaskDueDateTable.task_id] = id()
                    it[TaskDueDateTable.due_date] = dueDate()
                    it[TaskDueDateTable.created_at] = ceratedTime()
                }
            }

            TaskPriorityTable.insert {
                it[TaskPriorityTable.task_id] = id()
                it[TaskPriorityTable.priority] = planning.priority
                it[TaskPriorityTable.created_at] = ceratedTime()
            }
        }
}
