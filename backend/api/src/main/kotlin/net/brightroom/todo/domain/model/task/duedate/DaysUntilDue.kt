package net.brightroom.todo.domain.model.task.duedate

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * 期日までの日数
 */
@JvmInline
@Serializable
value class DaysUntilDue(private val value: Long) {
    operator fun invoke(): Long = value
    override fun toString(): String = value.toString()
    
    fun is期限超過(): Boolean = value < 0
    fun is期限当日(): Boolean = value == 0L
    fun isDueSoon(threshold: Long): Boolean = value in 1..threshold
}