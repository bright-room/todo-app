package net.brightroom.todo.domain.model.project.definition.responsibility

import net.brightroom.todo.domain.model.user.identity.UserId

/**
 * 担当者一覧
 */
data class Assignees(private val list: List<UserId> = listOf())
