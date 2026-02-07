package net.brightroom.todo.domain.model.task.planning.schedule.reminder

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class OffsetDaysTest {
    @Test
    fun `リマインド基準日が正常に生成できる`() {
        val result = runCatching { OffsetDays(1) }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `リマインド基準日が0より小さい場合エラーになる`() {
        assertFailsWith<IllegalArgumentException> { OffsetDays(-1) }
    }
}
