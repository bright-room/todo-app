package net.brightroom.todo.domain.model.label

import net.brightroom.todo.domain.model.label.content.Content

/**
 * ラベル
 */
data class Label(private val labelId: LabelId, private val content: Content)
