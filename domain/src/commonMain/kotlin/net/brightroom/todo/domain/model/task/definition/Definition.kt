package net.brightroom.todo.domain.model.task.definition

import net.brightroom.todo.domain.model.task.definition.classification.Classification
import net.brightroom.todo.domain.model.task.definition.content.Content
import net.brightroom.todo.domain.model.task.definition.responsibility.Responsibility

/**
 * タスクの定義
 */
data class Definition(
    private val content: Content,
    private val responsibility: Responsibility,
    private val classification: Classification,
)
