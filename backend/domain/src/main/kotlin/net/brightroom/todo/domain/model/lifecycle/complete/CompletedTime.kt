package net.brightroom.todo.domain.model.lifecycle.complete

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.policy.serializer.CompletedTimeSerializer

/**
 * 完了日時を表すインターフェース
 */
@Serializable(with = CompletedTimeSerializer::class)
interface CompletedTime {
    fun is完了日時が設定されている(): Boolean

    operator fun invoke(): LocalDateTime
}
