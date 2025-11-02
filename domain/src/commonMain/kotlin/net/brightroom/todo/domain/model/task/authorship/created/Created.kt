package net.brightroom.todo.domain.model.task.authorship.created

import net.brightroom.todo.domain.model.user.identity.UserId

/**
 * タスクの作成
 */
data class Created(private val createdAuthorId: UserId, private val createdTime: CreatedTime)
