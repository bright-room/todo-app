package net.brightroom.todo.domain.model.classification.tag

import kotlinx.serialization.Serializable

/**
 * タグ一覧
 */
@Serializable
data class Tags(
    private val list: List<Tag>,
) {
    operator fun invoke(): List<Tag> = list
}
