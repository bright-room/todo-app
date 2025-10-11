@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.annotation.Migratable
import org.jetbrains.exposed.v1.core.QueryAlias
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.alias
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.r2dbc.selectAll
import kotlin.uuid.ExperimentalUuidApi

@Migratable
object TaskContentTable : IntIdTable("task_content") {
    val task_id = reference("task_id", TaskIdTable, onDelete = ReferenceOption.CASCADE)
    val title = varchar("title", 200)
    val description = text("description")
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime).index()
}

val LatestTaskContent: QueryAlias
    get() =
        TaskContentTable
            .selectAll()
            .withDistinctOn(TaskContentTable.task_id)
            .orderBy(
                TaskContentTable.task_id to SortOrder.ASC,
                TaskContentTable.created_at to SortOrder.DESC,
            ).alias("latest_task_content")
