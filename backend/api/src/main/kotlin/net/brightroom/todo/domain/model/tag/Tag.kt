package net.brightroom.todo.domain.model.tag

import kotlinx.serialization.Serializable

/**
 * タグ集約
 */
@Serializable
data class Tag(
    val id: TagId,
    val title: TagTitle
) {
    /**
     * タイトルが一致するかどうかを判定する
     */
    fun hasTitle(title: TagTitle): Boolean = this.title == title

    /**
     * タイトルが一致するかどうかを判定する（文字列版）
     */
    fun hasTitle(title: String): Boolean = this.title.invoke() == title
}