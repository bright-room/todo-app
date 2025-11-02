package net.brightroom.todo.domain.model.project.authorship.updated

import net.brightroom.todo.domain.model.user.identity.UserId

/**
 * プロジェクトの最終更新
 */
data class LatestUpdated(private val latestUpdatedAuthorId: UserId, private val latestUpdatedTime: LatestUpdatedTime)
