package net.brightroom.todo.domain.model.taskgroup

import kotlinx.serialization.Serializable

/**
 * タスクグループ集約
 */
@Serializable
data class TaskGroup(
    val id: TaskGroupId,
    val title: TaskGroupTitle
) {
    /**
     * タイトルが一致するかどうかを判定する
     */
    fun hasTitle(title: TaskGroupTitle): Boolean = this.title == title

    /**
     * タイトルが一致するかどうかを判定する（文字列版）
     */
    fun hasTitle(title: String): Boolean = this.title.invoke() == title
}