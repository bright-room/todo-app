package net.brightroom.todo.domain.model.task.definition.classification

import net.brightroom.todo.domain.model.label.LabelIds
import net.brightroom.todo.domain.model.project.ProjectId

/**
 * タスクの分類
 */
data class Classification(
    private val projectId: ProjectId,
    private val category: Category,
    private val labelIds: LabelIds,
)
