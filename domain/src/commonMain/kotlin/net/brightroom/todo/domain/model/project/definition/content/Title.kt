package net.brightroom.todo.domain.model.project.definition.content

import kotlin.jvm.JvmInline

/**
 * プロジェクトタイトル
 */
@JvmInline
value class Title(private val value: String) {
    init {
        require(value.isNotBlank()) { "project title cannot be blank" }
        require(value.length <= 100) { "project title cannot be more than 100 characters" }
    }

    operator fun invoke(): String = value

    override fun toString(): String = value
}
