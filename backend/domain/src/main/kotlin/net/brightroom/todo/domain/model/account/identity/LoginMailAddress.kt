package net.brightroom.todo.domain.model.account.identity

import am.ik.yavi.builder.validator

/** ログイン用メールアドレス */
@JvmInline
value class LoginMailAddress(
    private val value: String,
) {
    operator fun invoke() = value

    override fun toString() = value

    companion object {
        val validator =
            validator {
                (LoginMailAddress::value)("ログイン用メールアドレス") {
                    notEmpty().message("メールアドレスが空")
                    lessThanOrEqual(254).message("メールアドレスは254文字以下")
                    email().message("メールアドレスの形式が不正")
                }
            }
    }
}
