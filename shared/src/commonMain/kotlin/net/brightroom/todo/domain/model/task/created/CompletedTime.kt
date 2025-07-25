package net.brightroom.todo.domain.model.task.created

import kotlinx.datetime.LocalDateTime

/**
 * タスク完了日時
 */
interface CompletedTime {
    operator fun invoke(): LocalDateTime
}
