package net.brightroom.todo.domain.model.task.relation

import net.brightroom.todo.domain.model.task.TaskId
import net.brightroom.todo.domain.model.task.TaskIds

/**
 * タスクの依存関係
 */
data class Relation(private val parentTaskId: TaskId, private val childTaskIds: TaskIds)
