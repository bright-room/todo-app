package net.brightroom.todo.domain.model.label.content

import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class ColorTest {
    @Test
    fun `カラーが正常に生成できる`() {
        val result = runCatching { Color("#FFFFFF") }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `カラーが空の場合エラーになる`() {
        assertFails { Color("") }
    }

    @Test
    fun `カラーの文字数が7文字未満の場合エラーになる`() {
        assertFails { Color("#FFFFF") }
    }

    @Test
    fun `カラーの文字数が7文字より大きいの場合エラーになる`() {
        assertFails { Color("#FFFFFFF") }
    }

    @Test
    fun `カラーの形式が不正の場合エラーになる`() {
        assertFails { Color("       ") }
        assertFails { Color("FFFFFFF") }
        assertFails { Color("#GFFFFF") }
    }
}
