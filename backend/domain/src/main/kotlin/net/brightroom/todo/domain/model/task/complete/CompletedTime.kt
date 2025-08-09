package net.brightroom.todo.domain.model.task.complete

import kotlinx.datetime.LocalDateTime

/**
 * 完了日時
 */
interface CompletedTime {
    operator fun invoke(): LocalDateTime
}
