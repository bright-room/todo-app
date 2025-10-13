package net.brightroom.todo.domain.model.task.definition.content

/**
 * タスクコンテンツ
 */
data class Content(private val title: Title, private val description: Description = Description())
