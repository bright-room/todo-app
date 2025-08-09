package net.brightroom.todo.domain.model.account.identity

/** ログイン用メールアドレス */
@JvmInline
value class LoginMailAddress(
    private val value: String,
) {
    operator fun invoke() = value

    override fun toString() = value
}
