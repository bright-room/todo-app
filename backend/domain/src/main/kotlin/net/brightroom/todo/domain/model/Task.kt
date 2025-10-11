package net.brightroom.todo.domain.model

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.identity.Identity
import net.brightroom.todo.domain.model.lifecycle.Lifecycle
import net.brightroom.todo.domain.model.planning.Planning

/**
 * タスク
 */
@Serializable
data class Task(
    val identity: Identity,
    val content: Content,
    val planning: Planning,
    val lifecycle: Lifecycle,
)
