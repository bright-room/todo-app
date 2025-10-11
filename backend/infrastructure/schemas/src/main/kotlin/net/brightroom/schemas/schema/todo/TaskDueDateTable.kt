@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.annotation.Migratable
import org.jetbrains.exposed.v1.core.QueryAlias
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.alias
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.date
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.r2dbc.selectAll
import kotlin.uuid.ExperimentalUuidApi

@Migratable
object TaskDueDateTable : IntIdTable("task_due_date") {
    val task_id = reference("task_id", TaskIdTable, onDelete = ReferenceOption.CASCADE)
    val due_date = date("due_date")
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime).index()
}

@Migratable
object InvalidTaskDueDateTable : Table("invalid_task_due_date") {
    val tack_due_date_id = reference("tack_due_date_id", TaskDueDateTable, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(tack_due_date_id)
}

val ActiveTaskDueDate: QueryAlias
    get() =
        TaskDueDateTable
            .leftJoin(InvalidTaskDueDateTable)
            .selectAll()
            .where { InvalidTaskDueDateTable.tack_due_date_id.isNull() }
            .alias("active_task_due_date")
