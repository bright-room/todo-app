@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.annotation.Migratable
import net.brightroom.todo.domain.model.planning.Priority
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
object TaskPriorityTable : IntIdTable("task_priority") {
    val task_id = reference("task_id", TaskIdTable, onDelete = ReferenceOption.CASCADE)
    val priority = enumerationByName<Priority>("priority", 10)
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime).index()
}

val LatestTaskPriority: QueryAlias
    get() =
        TaskPriorityTable
            .selectAll()
            .withDistinctOn(TaskPriorityTable.task_id)
            .orderBy(
                TaskPriorityTable.task_id to SortOrder.ASC,
                TaskPriorityTable.created_at to SortOrder.DESC,
            ).alias("latest_task_priority")
