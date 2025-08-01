package net.brightroom.todo._extensions.exposed

import org.jetbrains.exposed.v1.core.ColumnType
import org.jetbrains.exposed.v1.core.InternalApi
import org.jetbrains.exposed.v1.core.statements.api.RowApi
import org.jetbrains.exposed.v1.core.transactions.CoreTransactionManager
import org.jetbrains.exposed.v1.core.vendors.MariaDBDialect
import org.jetbrains.exposed.v1.core.vendors.currentDialect
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

@OptIn(ExperimentalUuidApi::class)
class UuidColumnType : ColumnType<Uuid>() {
    override fun sqlType(): String = currentDialect.dataTypeProvider.uuidType()

    override fun valueFromDB(value: Any): Uuid =
        when {
            value is Uuid -> value
            value is UUID -> value.toKotlinUuid()
            value is ByteArray -> Uuid.fromByteArray(value)
            value is String && value.matches(uuidRegexp) -> Uuid.parse(value)
            value is String -> Uuid.fromByteArray(value.toByteArray())
            else -> error("Unexpected value of type Uuid: $value of ${value::class.qualifiedName}")
        }

    override fun notNullValueToDB(value: Uuid): Any = currentDialect.dataTypeProvider.uuidToDB(value.toJavaUuid())

    override fun nonNullValueToString(value: Uuid): String = "'$value'"

    @Suppress("MagicNumber")
    override fun readObject(
        rs: RowApi,
        index: Int,
    ): Any? {
        @OptIn(InternalApi::class)
        val db = CoreTransactionManager.currentTransaction().db
        if (currentDialect is MariaDBDialect && !db.version.covers(10)) {
            return rs.getObject(index, java.sql.Array::class.java)
        }
        return super.readObject(rs, index)
    }

    companion object {
        private val uuidRegexp =
            Regex("[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}", RegexOption.IGNORE_CASE)
    }
}
