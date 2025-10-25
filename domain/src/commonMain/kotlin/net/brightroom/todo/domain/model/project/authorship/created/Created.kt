package net.brightroom.todo.domain.model.project.authorship.created

import net.brightroom.todo.domain.model.user.identity.UserId

/**
 * プロジェクトの作成
 */
data class Created(private val createdAuthorId: UserId, private val createdTime: CreatedTime)
