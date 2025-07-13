package net.brightroom.todo.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class SubTaskId(val value: String) {
    companion object {
        fun generate(): SubTaskId = SubTaskId(UUID.randomUUID().toString())
        fun from(value: String): SubTaskId = SubTaskId(value)
    }
}

@OptIn(ExperimentalTime::class)
@Serializable
data class SubTask(
    val id: SubTaskId,
    val parentTaskId: TaskId,
    val title: String,
    val description: String = "",
    @Contextual val createdAt: Instant,
    val isCompleted: Boolean = false,
    @Contextual val completedAt: Instant? = null
) {
    init {
        require(title.isNotBlank()) { "SubTask title cannot be blank" }
        require(title.length <= 200) { "SubTask title cannot exceed 200 characters" }
    }

    fun complete(completedAt: Instant): SubTask {
        return copy(isCompleted = true, completedAt = completedAt)
    }

    fun uncomplete(): SubTask {
        return copy(isCompleted = false, completedAt = null)
    }

    fun updateTitle(newTitle: String): SubTask {
        require(newTitle.isNotBlank()) { "SubTask title cannot be blank" }
        require(newTitle.length <= 200) { "SubTask title cannot exceed 200 characters" }
        return copy(title = newTitle)
    }

    fun updateDescription(newDescription: String): SubTask {
        return copy(description = newDescription)
    }
}