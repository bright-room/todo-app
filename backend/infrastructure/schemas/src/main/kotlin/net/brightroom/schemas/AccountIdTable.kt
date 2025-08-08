package net.brightroom.schemas

import net.brightroom.migration.Migratable
import net.brightroom.schemas._extensions.exposed.KotlinUUIDTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

@Migratable
object AccountIdTable : KotlinUUIDTable("account_id") {
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime).index()
}
