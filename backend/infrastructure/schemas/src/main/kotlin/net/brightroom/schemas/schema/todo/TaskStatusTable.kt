@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.annotation.Migratable
import net.brightroom.todo.domain.model.lifecycle.Status
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
object TaskStatusTable : IntIdTable("task_status") {
    val task_id = reference("task_id", TaskIdTable, onDelete = ReferenceOption.CASCADE)
    val status = enumerationByName<Status>("status", 5)
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime).index()
}

val LatestTaskStatus: QueryAlias
    get() =
        TaskStatusTable
            .selectAll()
            .withDistinctOn(TaskStatusTable.task_id)
            .orderBy(
                TaskStatusTable.task_id to SortOrder.ASC,
                TaskStatusTable.created_at to SortOrder.DESC,
            ).alias("latest_task_status")
