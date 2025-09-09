@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.detector.Migratable
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

@Migratable
object PasswordTable : IntIdTable("password") {
    val account_id = reference("account_id", AccountIdTable)
    val hash = varchar("hash", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
