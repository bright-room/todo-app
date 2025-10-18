package net.brightroom.todo.domain.model.identity

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import net.brightroom._extensions.kotlinx.datetime.now

/**
 * 作成日時
 */
@JvmInline
@Serializable
value class CreatedTime(private val value: LocalDateTime) {
    operator fun invoke(): LocalDateTime = value

    override fun toString(): String = value.toString()

    companion object {
        fun now(): CreatedTime = CreatedTime(LocalDateTime.now())
    }
}
