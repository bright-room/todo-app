package net.brightroom.todo.domain.model.lifecycle.complete

import kotlinx.datetime.LocalDateTime

/**
 * 完了日時を表すインターフェース
 */
interface CompletedTime {
    operator fun invoke(): LocalDateTime
}
