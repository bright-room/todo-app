@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.domain.model.project

import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * プロジェクトID
 */
@JvmInline
value class ProjectId(private val value: Uuid) {
    operator fun invoke(): Uuid = value

    override fun toString(): String = value.toString()
}
