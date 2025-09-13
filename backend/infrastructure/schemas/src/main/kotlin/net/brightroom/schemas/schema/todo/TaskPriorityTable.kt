@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.annotation.Migratable
import net.brightroom.todo.domain.model.planning.Priority
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

@Migratable
object TaskPriorityTable : IntIdTable("task_priority") {
    val task_id = reference("task_id", TaskIdTable)
    val priority = enumerationByName<Priority>("priority", 10)
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
