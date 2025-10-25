package net.brightroom.todo.domain.model.project.definition.content

import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TitleTest {
    @Test
    fun `プロジェクトのタイトルが正常に生成できる`() {
        val result =
            runCatching {
                Title("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
            }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `プロジェクトのタイトルが空文字の場合エラーになる`() {
        assertFailsWith<IllegalArgumentException> { Title("") }
        assertFailsWith<IllegalArgumentException> { Title(" ") }
    }

    @Test
    fun `プロジェクトのタイトルが100文字を超える場合エラーになる`() {
        assertFailsWith<IllegalArgumentException> {
            Title("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        }
    }
}
