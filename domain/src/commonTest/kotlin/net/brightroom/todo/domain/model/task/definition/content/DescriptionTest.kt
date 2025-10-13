package net.brightroom.todo.domain.model.task.definition.content

import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class DescriptionTest {
    @Test
    fun `タスクの説明が正常に生成できる`() {
        val result = runCatching { Description("a".repeat(65_536)) }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `タスクの説明が65,536文字を超える場合エラーになる`() {
        assertFailsWith<IllegalArgumentException> { Description("a".repeat(65_537)) }
    }
}
