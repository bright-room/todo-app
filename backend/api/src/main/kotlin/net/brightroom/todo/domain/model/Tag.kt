package net.brightroom.todo.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class TagId(val value: String) {
    companion object {
        fun generate(): TagId = TagId(UUID.randomUUID().toString())
        fun from(value: String): TagId = TagId(value)
    }
}

@OptIn(ExperimentalTime::class)
@Serializable
data class Tag(
    val id: TagId,
    val title: String,
    @Contextual val createdAt: Instant
) {
    init {
        require(title.isNotBlank()) { "Tag title cannot be blank" }
        require(title.length <= 30) { "Tag title cannot exceed 30 characters" }
    }

    fun updateTitle(newTitle: String): Tag {
        require(newTitle.isNotBlank()) { "Tag title cannot be blank" }
        require(newTitle.length <= 30) { "Tag title cannot exceed 30 characters" }
        return copy(title = newTitle)
    }
}
