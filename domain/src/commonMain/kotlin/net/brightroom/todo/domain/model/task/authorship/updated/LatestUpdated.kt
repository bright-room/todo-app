package net.brightroom.todo.domain.model.task.authorship.updated

import net.brightroom.todo.domain.model.user.identity.UserId

/**
 * タスクの最終更新
 */
data class LatestUpdated(private val latestUpdatedAuthorId: UserId, private val latestUpdatedTime: LatestUpdatedTime)
