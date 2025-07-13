package net.brightroom.todo.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class TaskReminderId(val value: String) {
    companion object {
        fun generate(): TaskReminderId = TaskReminderId(UUID.randomUUID().toString())
        fun from(value: String): TaskReminderId = TaskReminderId(value)
    }
}

@OptIn(ExperimentalTime::class)
@Serializable
data class TaskReminder(
    val id: TaskReminderId,
    val taskId: TaskId,
    @Contextual val reminderDateTime: Instant
) {
    fun updateReminderDateTime(newDateTime: Instant): TaskReminder {
        return copy(reminderDateTime = newDateTime)
    }
}