package net.brightroom.todo.infrastructure.datasource.exposed

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

object TaskContentTable : IntIdTable("task_content") {
    @OptIn(ExperimentalUuidApi::class)
    val taskId = reference("task_id", TaskIdTable)
    val title = varchar("title", 200)
    val description = text("description")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
