package net.brightroom.todo.domain.model.content

import kotlinx.serialization.Serializable

/**
 * 説明
 */
@JvmInline
@Serializable
value class Description(private val value: String) {
    operator fun invoke(): String = value

    override fun toString(): String = value
}
