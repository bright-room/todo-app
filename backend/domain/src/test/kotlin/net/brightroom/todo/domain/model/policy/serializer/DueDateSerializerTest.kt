package net.brightroom.todo.domain.model.policy.serializer

import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import net.brightroom.todo.domain.model.planning.due.DueDateFactory
import net.brightroom.todo.domain.model.planning.due.NoSetDueDate
import net.brightroom.todo.domain.model.planning.due.SetDueDate
import net.brightroom.todo.domain.policy.serializer.DueDateSerializer
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import kotlin.test.assertEquals

@DisplayName("期限日のシリアライズ/デシリアライズテスト")
class DueDateSerializerTest {
    private val json = Json

    @Test
    fun `期限日が存在する場合、ISO8601(日付)形式にシリアライズできる`() {
        val date = LocalDate.parse("2025-09-24")
        val dueDate = DueDateFactory.create(date)

        val actual = json.encodeToString(DueDateSerializer, dueDate)
        assertEquals("\"2025-09-24\"", actual)
    }

    @Test
    fun `期限日が存在しない場合、nullという文字列でシリアライズできる`() {
        val dueDate = DueDateFactory.create(null)

        val actual = json.encodeToString(DueDateSerializer, dueDate)
        assertEquals("null", actual)
    }

    @Test
    fun `ISO8601(日付)形式の文字列を渡された場合、DueDateにデシリアライズできる`() {
        val actual = json.decodeFromString(DueDateSerializer, "\"2025-09-24\"")

        val date = LocalDate.parse("2025-09-24")
        val expected = DueDateFactory.create(date)

        assertInstanceOf<SetDueDate>(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun `nullの文字列を渡された場合、空のDueDateにデシリアライズできる`() {
        val actual = json.decodeFromString(DueDateSerializer, "null")
        assertInstanceOf<NoSetDueDate>(actual)
    }
}
