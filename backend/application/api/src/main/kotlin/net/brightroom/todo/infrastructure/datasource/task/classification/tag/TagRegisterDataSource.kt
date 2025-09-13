package net.brightroom.todo.infrastructure.datasource.task.classification.tag

import net.brightroom.schemas.schema.todo.TagsTable
import net.brightroom.schemas.transaction.transaction
import net.brightroom.todo.domain.model.classification.tag.Tag
import net.brightroom.todo.infrastructure.datasource.CreatedTime
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.jetbrains.exposed.v1.r2dbc.insertAndGetId

class TagRegisterDataSource(
    private val db: R2dbcDatabase,
) : TagRegisterRepository {
    override suspend fun register(tag: Tag): TagId =
        transaction(db) {
            val createdTime = CreatedTime.now()
            val tagId =
                TagsTable.insertAndGetId {
                    it[TagsTable.name] = tag()
                    it[TagsTable.created_at] = createdTime()
                }

            TagId(tagId.value)
        }
}
