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

@DisplayName("バージョンテスト")
class VersionTest {
    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `バージョンを生成できる`() {
        mockkStatic(LocalDateTime::now)
        val fixed = LocalDateTime(2024, 1, 2, 3, 4, 5, 678_000_000)
        every { LocalDateTime.now(any()) } returns fixed

        val version = Version()
        assertEquals("V20240102030405678", version.asText())
    }
}
