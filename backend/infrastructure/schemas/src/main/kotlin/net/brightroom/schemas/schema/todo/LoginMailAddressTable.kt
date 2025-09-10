@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.schemas.schema.todo

import net.brightroom.migration.annotation.Migratable
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.uuid.ExperimentalUuidApi

@Migratable
object LoginMailAddressTable : IntIdTable("login_mail_address") {
    val account_id = reference("account_id", AccountIdTable)
    val mail_address = varchar("mail_address", 128)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
