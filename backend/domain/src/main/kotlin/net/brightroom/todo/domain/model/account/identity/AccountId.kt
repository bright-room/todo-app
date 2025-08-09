@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.domain.model.account.identity

import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/** アカウントid */
@JvmInline
value class AccountId(
    private val value: Uuid,
) {
    operator fun invoke() = value

    override fun toString() = value.toString()
}
