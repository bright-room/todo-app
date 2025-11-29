package net.brightroom.todo.domain.model.task.lifecycle

import net.brightroom.todo.domain.model.project.lifecycle.transition.Transition

/**
 * タスクのライフサイクル
 */
data class Lifecycle(private val state: State, private val transition: Transition)
