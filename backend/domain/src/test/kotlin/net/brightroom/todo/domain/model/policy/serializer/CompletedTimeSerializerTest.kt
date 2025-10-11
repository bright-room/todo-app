package net.brightroom.todo.domain.model.policy.serializer

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.brightroom.todo.domain.model.lifecycle.complete.CompletedTimeFactory
import net.brightroom.todo.domain.model.lifecycle.complete.NoSetCompletedTime
import net.brightroom.todo.domain.model.lifecycle.complete.SetCompletedTime
import net.brightroom.todo.domain.policy.serializer.CompletedTimeSerializer
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import kotlin.test.assertEquals

@DisplayName("完了日時のシリアライズ/デシリアライズテスト")
class CompletedTimeSerializerTest {
    private val json = Json

    @Test
    fun `完了日時が存在する場合、ISO8601形式にシリアライズできる`() {
        val datetime = LocalDateTime.parse("2025-09-24T16:45:30")
        val completedTime = CompletedTimeFactory.create(datetime)

        val actual = json.encodeToString(CompletedTimeSerializer, completedTime)
        assertEquals("\"2025-09-24T16:45:30\"", actual)
    }

    @Test
    fun `完了日時が存在しない場合、nullという文字列でシリアライズできる`() {
        val completedTime = CompletedTimeFactory.create(null)

        val actual = json.encodeToString(CompletedTimeSerializer, completedTime)
        assertEquals("null", actual)
    }

    @Test
    fun `ISO8601形式の文字列を渡された場合、CompletedTimeにデシリアライズできる`() {
        val actual = json.decodeFromString(CompletedTimeSerializer, "\"2025-09-24T16:45:30\"")

        val datetime = LocalDateTime.parse("2025-09-24T16:45:30")
        val expected = CompletedTimeFactory.create(datetime)

        assertInstanceOf<SetCompletedTime>(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun `nullの文字列を渡された場合、空のCompletedTimeにデシリアライズできる`() {
        val actual = json.decodeFromString(CompletedTimeSerializer, "null")
        assertInstanceOf<NoSetCompletedTime>(actual)
    }
}
