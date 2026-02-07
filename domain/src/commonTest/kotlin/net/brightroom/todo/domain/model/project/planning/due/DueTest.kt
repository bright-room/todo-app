package net.brightroom.todo.domain.model.project.planning.due

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import net.brightroom._extensions.kotlinx.datetime.now
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DueTest {
    @Test
    fun `未設定のプロジェクトの予定期限を生成できる`() {
        val due = Due.of()
        assertEquals(Due.Unset, due)
    }

    @Test
    fun `設定済みのプロジェクトの予定期限を生成できる`() {
        val date = LocalDate.now()

        val due = Due.of(BeginDate(date), DueDate(date.plus(DatePeriod(days = 1))))
        assertEquals(Due.Set(BeginDate(date), DueDate(date.plus(DatePeriod(days = 1)))), due)
    }

    @Test
    fun `プロジェクトの予定期限日が開始予定日よりも前の日付の場合エラーになる`() {
        val date = LocalDate.now()

        assertFailsWith<IllegalArgumentException> {
            Due.of(BeginDate(date.plus(DatePeriod(days = 1))), DueDate(date))
        }
    }
}
