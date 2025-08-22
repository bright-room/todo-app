package net.brightroom.todo.domain.model.account.authentication

import am.ik.yavi.builder.validator
import kotlin.jvm.JvmInline

/** パスワード */
@JvmInline
value class Password(
    private val value: String,
) {
    operator fun invoke() = value

    override fun toString() = value

    companion object {
        val validator =
            validator {
                (Password::value)("パスワード") {
                    notEmpty().message("パスワードが空")
                    greaterThanOrEqual(16).message("パスワードは16文字以上")
                    lessThanOrEqual(20).message("パスワードは20文字以下")
                    pattern("^[0-9a-zA-Z!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]+$").message("パスワードに使用できない文字が含まれています")
                    pattern(".*[A-Z].*").message("パスワードに大文字英字が含まれていません")
                    pattern(".*[a-z].*").message("パスワードに小文字英字が含まれていません")
                    pattern(".*[0-9].*").message("パスワードに数字が含まれていません")
                    pattern(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*").message("パスワードに記号が含まれていません")
                }
            }
    }
}
