package net.brightroom.todo._extensions.exposed

import org.jetbrains.exposed.v1.core.ColumnType
import org.jetbrains.exposed.v1.core.InternalApi
import org.jetbrains.exposed.v1.core.statements.api.RowApi
import org.jetbrains.exposed.v1.core.transactions.CoreTransactionManager
import org.jetbrains.exposed.v1.core.vendors.H2Dialect
import org.jetbrains.exposed.v1.core.vendors.MariaDBDialect
import org.jetbrains.exposed.v1.core.vendors.currentDialect
import java.nio.ByteBuffer
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalUuidApi::class)
class UuidColumnType : ColumnType<Uuid>() {
    override fun sqlType(): String = currentDialect.dataTypeProvider.uuidType()

    override fun valueFromDB(value: Any): Uuid =
        when {
            value is Uuid -> value
            value is ByteArray -> ByteBuffer.wrap(value).let { b -> Uuid.fromLongs(b.long, b.long) }
            value is String && value.matches(uuidRegexp) -> Uuid.parse(value)
            value is String -> ByteBuffer.wrap(value.toByteArray()).let { b -> Uuid.fromLongs(b.long, b.long) }
            value is ByteBuffer -> value.let { b -> Uuid.fromLongs(b.long, b.long) }
            else -> error("Unexpected value of type Uuid: $value of ${value::class.qualifiedName}")
        }

    override fun notNullValueToDB(value: Uuid): Any =
        ((currentDialect as? H2Dialect)?.originalDataTypeProvider ?: currentDialect.dataTypeProvider)
            .uuidToDB(value.toJavaUuid())

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
