package net.brightroom.todo.domain.model.account.authentication

import kotlin.jvm.JvmInline

/** パスワードハッシュ */
@JvmInline
value class PasswordHash(
    private val value: String,
) {
    operator fun invoke() = value

    override fun toString() = value
}
