@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.annotation.Migratable
import net.brightroom.schemas.schema.extensions.uuid.KotlinUUIDTable
import org.jetbrains.exposed.v1.core.QueryAlias
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.SqlExpressionBuilder.isNull
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.alias
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.r2dbc.selectAll
import kotlin.uuid.ExperimentalUuidApi

@Migratable
object TaskIdTable : KotlinUUIDTable("task_id") {
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime).index()
}

@Migratable
object TrashedTaskTable : Table("trashed_task") {
    val task_id = reference("task_id", TaskIdTable, onDelete = ReferenceOption.CASCADE)
    val trashed_at = datetime("trashed_at").defaultExpression(CurrentDateTime).index()
}

val ActiveTask: QueryAlias
    get() =
        TaskIdTable
            .leftJoin(TrashedTaskTable)
            .selectAll()
            .where { TrashedTaskTable.trashed_at.isNull() }
            .alias("active_task")
