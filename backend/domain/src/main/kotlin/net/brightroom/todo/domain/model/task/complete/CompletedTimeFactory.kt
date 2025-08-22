package net.brightroom.todo.domain.model.task.complete

import kotlinx.datetime.LocalDateTime

class CompletedTimeFactory {
    companion object {
        fun create(value: LocalDateTime? = null): CompletedTime {
            if (value == null) return NoSetCompletedTime()
            return SetCompletedTime(value)
        }
    }
}
