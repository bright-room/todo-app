package net.brightroom.todo.infrastructure.datasource

import kotlinx.datetime.LocalDateTime
import net.brightroom.todo._extensions.kotlinx.datetime.now

/**
 * 作成日時
 */
@JvmInline
value class CreatedTime(
    private val value: LocalDateTime,
) {
    constructor() : this(LocalDateTime.Companion.now())

    operator fun invoke(): LocalDateTime = value

    override fun toString(): String = value.toString()
}
