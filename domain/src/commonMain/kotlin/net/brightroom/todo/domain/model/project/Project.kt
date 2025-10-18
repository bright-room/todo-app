package net.brightroom.todo.domain.model.project

import net.brightroom.todo.domain.model.project.authorship.Authorship
import net.brightroom.todo.domain.model.project.definition.Definition
import net.brightroom.todo.domain.model.project.lifecycle.Lifecycle
import net.brightroom.todo.domain.model.project.planning.Planning

/**
 * プロジェクト
 */
data class Project(
    private val projectId: ProjectId,
    private val definition: Definition,
    private val planning: Planning,
    private val lifecycle: Lifecycle,
    private val authorship: Authorship,
)
