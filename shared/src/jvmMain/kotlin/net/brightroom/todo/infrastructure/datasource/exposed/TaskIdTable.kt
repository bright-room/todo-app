package net.brightroom.todo.infrastructure.datasource.exposed

import net.brightroom.todo._extensions.exposed.UuidIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

object TaskIdTable : UuidIdTable("task_id") {
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
