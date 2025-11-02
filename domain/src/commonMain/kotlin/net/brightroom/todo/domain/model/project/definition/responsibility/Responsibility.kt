package net.brightroom.todo.domain.model.project.definition.responsibility

import net.brightroom.todo.domain.model.task.definition.responsibility.Assignees
import net.brightroom.todo.domain.model.user.identity.UserId

/**
 * プロジェクトの役割
 */
data class Responsibility(private val ownerId: UserId, private val assignees: Assignees)
