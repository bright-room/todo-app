package net.brightroom.todo.domain.model.label.content

import kotlin.jvm.JvmInline

/**
 * ラベル名
 */
@JvmInline
value class Name(private val value: String) {
    init {
        require(value.isNotBlank()) { "Name cannot be blank" }
        require(value.length <= 50) { "Name cannot be more than 50 characters" }
    }

    operator fun invoke(): String = value

    override fun toString(): String = value
}
