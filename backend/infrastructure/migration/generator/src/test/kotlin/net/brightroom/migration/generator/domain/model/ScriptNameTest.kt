package net.brightroom.migration.generator.domain.model

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.datetime.LocalDateTime
import net.brightroom._extensions.kotlinx.datetime.now
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("スクリプトファイル名テスト")
class ScriptNameTest {
    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `createでバージョンとサフィックスが結合される`() {
        mockkStatic(LocalDateTime::now)
        val fixed = LocalDateTime(2024, 12, 31, 23, 59, 1, 2_000_000)
        every { LocalDateTime.now(any()) } returns fixed

        val version = Version()
        val actual = ScriptName.create(version, "create_table")

        assertEquals("V20241231235901002__create_table", actual.toString())
    }
}
