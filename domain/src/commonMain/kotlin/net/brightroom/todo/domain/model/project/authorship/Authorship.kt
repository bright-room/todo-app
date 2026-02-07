package net.brightroom.todo.domain.model.project.authorship

import net.brightroom.todo.domain.model.project.authorship.created.Created
import net.brightroom.todo.domain.model.project.authorship.updated.LatestUpdated

/**
 * プロジェクト監査
 */
data class Authorship(private val created: Created, private val latestUpdated: LatestUpdated)
