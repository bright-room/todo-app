@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.domain.model.user.identity

import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * ユーザーを表す識別子
 */
@JvmInline
value class UserId(private val value: Uuid) {
    operator fun invoke(): Uuid = value

    override fun toString(): String = value.toString()
}
