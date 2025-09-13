@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.infrastructure.datasource.task.classification

import net.brightroom.schemas.schema.todo.TaskAssociatedTagsTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.application.repository.task.classification.TaskClassificationRegisterRepository
import net.brightroom.todo.domain.model.classification.Classification
import net.brightroom.todo.domain.model.identity.Id
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import net.brightroom.todo.infrastructure.datasource.task.classification.tag.TagRegisterRepository
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insert
import kotlin.uuid.ExperimentalUuidApi

class TaskClassificationRegisterDataSource(
    private val tagRegisterRepository: TagRegisterRepository,
    private val db: R2dbcDatabase,
) : TaskClassificationRegisterRepository {
    override suspend fun register(
        classification: Classification,
        id: Id,
    ): Unit =
        transaction(db) {
            val createdTime = CreatedTime.now()

            val tags = classification.tags
            tags().forEach { tag ->
                val tagId = tagRegisterRepository.register(tag)
                TaskAssociatedTagsTable.insert {
                    it[TaskAssociatedTagsTable.task_id] = id()
                    it[TaskAssociatedTagsTable.tag_id] = tagId()
                    it[TaskAssociatedTagsTable.created_at] = createdTime()
                }
            }
        }
}
