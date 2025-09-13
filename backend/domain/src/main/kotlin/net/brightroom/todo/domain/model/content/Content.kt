package net.brightroom.todo.domain.model.content

import am.ik.yavi.builder.validator

/**
 * コンテンツ
 */
data class Content(
    val title: Title,
    val description: Description,
) {
    companion object {
        val validator = validator {
            Content::title nest Title.validator
        }
    }
}
