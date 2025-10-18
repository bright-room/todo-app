package net.brightroom.todo.domain.model.user.identity

import net.brightroom.todo.domain.validator.mailaddress.MailAddressValidator
import kotlin.jvm.JvmInline

/**
 * ログインメールアドレス
 */
@JvmInline
value class LoginMailAddress(private val value: String) {
    init {
        val validator = MailAddressValidator(value)
        validator.validate()
    }

    operator fun invoke(): String = value

    override fun toString(): String = value
}
