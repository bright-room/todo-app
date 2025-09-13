package net.brightroom.schemas.schema.todo

import net.brightroom.migration.annotation.Migratable
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

@Migratable
object TaskTagTable : IntIdTable("task_tag") {
    val name = varchar("name", 255)
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
