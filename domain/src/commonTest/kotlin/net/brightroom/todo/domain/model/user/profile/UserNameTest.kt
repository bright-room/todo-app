package net.brightroom.todo.domain.model.user.profile

import kotlin.test.Test
import kotlin.test.assertFailsWith

class UserNameTest {
    @Test
    fun `ユーザー名が正常に生成できる`() {
        val result = runCatching { UserName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa") }
    }

    @Test
    fun `ユーザー名が空の場合エラーになる`() {
        assertFailsWith<IllegalArgumentException> { UserName("") }
        assertFailsWith<IllegalArgumentException> { UserName(" ") }
    }

    @Test
    fun `ユーザー名の文字数が50文字を超える場合エラーになる`() {
        assertFailsWith<IllegalArgumentException> { UserName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa") }
    }
}
