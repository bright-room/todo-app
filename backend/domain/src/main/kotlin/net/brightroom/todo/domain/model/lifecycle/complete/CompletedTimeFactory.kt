package net.brightroom.todo.domain.model.lifecycle.complete

import kotlinx.datetime.LocalDateTime

/**
 * 完了日時生成
 */
object CompletedTimeFactory {
    fun create(value: LocalDateTime?): CompletedTime {
        if (value == null) return NoSetCompletedTime()
        return SetCompletedTime(value)
    }
}
