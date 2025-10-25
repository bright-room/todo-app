package net.brightroom.todo.domain.model.label.content

import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class NameTest {
    @Test
    fun `ラベル名が正常に生成できる`() {
        val result = runCatching { Name("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa") }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `ラベル名が空文字の場合エラーになる`() {
        assertFails { Name("") }
        assertFails { Name(" ") }
    }

    @Test
    fun `ラベル名の文字数は50文字を超える場合エラーになる`() {
        assertFails { Name("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa") }
    }
}
