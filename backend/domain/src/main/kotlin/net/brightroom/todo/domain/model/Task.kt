package net.brightroom.todo.domain.model

import net.brightroom.todo.domain.model.classification.Classification
import net.brightroom.todo.domain.model.content.Content
import net.brightroom.todo.domain.model.identity.Identity
import net.brightroom.todo.domain.model.lifecycle.Lifecycle
import net.brightroom.todo.domain.model.planning.Planning

/**
 * タスク
 */
data class Task(
    val identity: Identity,
    val content: Content,
    val planning: Planning,
    val lifecycle: Lifecycle,
    val classification: Classification,
)
