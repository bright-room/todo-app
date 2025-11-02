package net.brightroom.todo.domain.model.task

import net.brightroom.todo.domain.model.task.authorship.Authorship
import net.brightroom.todo.domain.model.task.definition.Definition
import net.brightroom.todo.domain.model.task.lifecycle.Lifecycle
import net.brightroom.todo.domain.model.task.planning.Planning
import net.brightroom.todo.domain.model.task.relation.Relation

/**
 * タスク
 */
data class Task(
    private val taskId: TaskId,
    private val definition: Definition,
    private val planning: Planning,
    private val lifecycle: Lifecycle,
    private val authorship: Authorship,
    private val relation: Relation,
)
