@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas

import net.brightroom.migration.Migratable
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

@Migratable(order = 2)
object TaskContentTable : IntIdTable("task_content") {
    val task_id = reference("task_id", TaskIdTable)
    val title = varchar("title", 200)
    val description = text("description")
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
