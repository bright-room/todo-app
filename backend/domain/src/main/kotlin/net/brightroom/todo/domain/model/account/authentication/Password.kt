package net.brightroom.todo.domain.model.account.authentication

import kotlin.jvm.JvmInline

/** パスワード */
@JvmInline
value class Password(
    private val value: String,
) {
    operator fun invoke() = value

    override fun toString() = value
}
