package net.brightroom.todo.domain.model.content

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable

/**
 * コンテンツ
 */
@Serializable
data class Content(val title: Title, val description: Description = Description("")) {
    companion object {
        val validator =
            validator {
                Content::title nest Title.validator
            }
    }
}
