package net.brightroom.todo.domain.model.classification

import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.classification.tag.Tags

/**
 * 分類
 */
@Serializable
data class Classification(
    val tags: Tags = Tags(emptyList()),
)
