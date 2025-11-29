package net.brightroom.todo.domain.model.project.definition

import net.brightroom.todo.domain.model.project.definition.content.Content
import net.brightroom.todo.domain.model.project.definition.responsibility.Responsibility

/**
 * プロジェクトの定義
 */
data class Definition(private val content: Content, private val responsibility: Responsibility)
