package net.brightroom.todo.domain.model.task.content

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TitleTest {
    @Test
    fun タスクのタイトルが生成できる() {
        val title = Title("Sample Title")
        assertEquals("Sample Title", title())
    }

    @Test
    fun 空文字のタイトルは作成できない() {
        assertFailsWith<IllegalArgumentException> {
            Title("")
        }
    }

    @Test
    fun 空白のタイトルは作成できない() {
        assertFailsWith<IllegalArgumentException> {
            Title(" ")
        }
    }
}
