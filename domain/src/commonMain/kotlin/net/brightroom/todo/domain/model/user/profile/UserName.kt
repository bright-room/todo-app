package net.brightroom.todo.domain.model.user.profile

import kotlin.jvm.JvmInline

/**
 * ユーザー名
 */
@JvmInline
value class UserName(private val value: String) {
    init {
        require(value.isNotBlank()) { "User name cannot be blank" }
        require(value.length <= 50) { "User name cannot be more than 50 characters" }
    }

    operator fun invoke(): String = value

    override fun toString(): String = value
}
