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
object TaskCompleteTimeTable : IntIdTable("task_complete_time") {
    val task_id = reference("task_id", TaskIdTable, onDelete = ReferenceOption.CASCADE)
    val completed_at = datetime("completed_at").defaultExpression(CurrentDateTime).index()
}

val LatestTaskCompleteTime: QueryAlias
    get() =
        TaskCompleteTimeTable
            .selectAll()
            .withDistinctOn(TaskCompleteTimeTable.task_id)
            .orderBy(
                TaskCompleteTimeTable.task_id to SortOrder.ASC,
                TaskCompleteTimeTable.completed_at to SortOrder.DESC,
            ).alias("latest_task_complete_time")
