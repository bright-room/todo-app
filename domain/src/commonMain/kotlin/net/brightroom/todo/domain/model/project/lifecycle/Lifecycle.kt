package net.brightroom.todo.domain.model.project.lifecycle

import net.brightroom.todo.domain.model.project.lifecycle.transition.Transition

/**
 * プロジェクトのライフサイクル
 */
data class Lifecycle(private val state: State, private val transition: Transition)
