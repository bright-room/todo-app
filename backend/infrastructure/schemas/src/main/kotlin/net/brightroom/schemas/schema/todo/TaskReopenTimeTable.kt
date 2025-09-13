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
object TaskReopenTimeTable : IntIdTable("task_reopen_time") {
    val task_id = reference("task_id", TaskIdTable, onDelete = ReferenceOption.CASCADE)
    val reopened_at = datetime("reopened_at").defaultExpression(CurrentDateTime).index()
}

val LatestTaskReopenTime: QueryAlias
    get() =
        TaskReopenTimeTable
            .selectAll()
            .withDistinctOn(TaskReopenTimeTable.task_id)
            .orderBy(
                TaskReopenTimeTable.task_id to SortOrder.ASC,
                TaskReopenTimeTable.reopened_at to SortOrder.DESC,
            ).alias("latest_task_reopen_time")
