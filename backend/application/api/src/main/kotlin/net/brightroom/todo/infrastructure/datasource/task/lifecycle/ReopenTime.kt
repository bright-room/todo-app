package net.brightroom.todo.infrastructure.datasource.task.lifecycle

import kotlinx.datetime.LocalDateTime
import net.brightroom._extensions.kotlinx.datetime.now

@JvmInline
value class ReopenTime(private val value: LocalDateTime) {
    operator fun invoke(): LocalDateTime = value

    override fun toString(): String = value.toString()

    companion object {
        fun now(): ReopenTime = ReopenTime(LocalDateTime.now())
    }
}
