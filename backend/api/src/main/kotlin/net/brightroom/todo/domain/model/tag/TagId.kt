package net.brightroom.todo.domain.model.tag

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * タグID
 */
@JvmInline
@Serializable
value class TagId(private val value: String) {
    operator fun invoke(): String = value
    override fun toString(): String = value

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun generate(): TagId = TagId(Uuid.random().toString())
    }
}