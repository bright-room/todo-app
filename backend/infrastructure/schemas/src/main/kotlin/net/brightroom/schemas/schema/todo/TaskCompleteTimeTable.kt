@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.detector.Migratable
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

@Migratable
object TaskCompleteTimeTable : IntIdTable("task_complete_time") {
    val taskId = reference("task_id", TaskIdTable)
    val completed_at = datetime("completed_at").defaultExpression(CurrentDateTime).index()
}
