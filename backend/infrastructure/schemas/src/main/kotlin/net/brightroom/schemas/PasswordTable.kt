@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas

import net.brightroom.migration.Migratable
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

@Migratable(order = 1)
object PasswordTable : IntIdTable("password") {
    val account_id = reference("account_id", AccountIdTable)
    val hash = varchar("hash", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
