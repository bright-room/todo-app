package net.brightroom.todo.domain.model.project.definition.content

import kotlin.jvm.JvmInline

/**
 * プロジェクトの説明
 */
@JvmInline
value class Description(private val value: String) {
    init {
        require(value.length <= 65_536) { "project description cannot be more than 65,536 characters" }
    }

    operator fun invoke(): String = value

    override fun toString(): String = value
}
