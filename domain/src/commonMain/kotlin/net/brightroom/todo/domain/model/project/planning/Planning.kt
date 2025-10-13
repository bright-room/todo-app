package net.brightroom.todo.domain.model.project.planning

import net.brightroom.todo.domain.model.project.planning.due.Due

/**
 * プロジェクトの計画
 */
data class Planning(private val priority: Priority, private val due: Due)
