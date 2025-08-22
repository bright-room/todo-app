package net.brightroom.todo.domain.model.account.identity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("ログイン用メールアドレステスト")
class LoginMailAddressTest {
    @ParameterizedTest
    @ValueSource(
        strings = [
            "test@example.com",
            "user.name@domain.co.jp",
            "test123+tag@subdomain.example.org",
            "a@b.co",
            "very.long.email.address.with.many.dots@very.long.domain.name.with.many.subdomains.example.com",
        ],
    )
    fun `メールアドレスが正常に生成できる`(value: String) {
        val loginMailAddress = LoginMailAddress(value)

        val validator = LoginMailAddress.validator
        val violations = validator.validate(loginMailAddress)

        assertEquals(0, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
        ],
    )
    fun `メールアドレスが空の場合エラーとなる`(value: String) {
        val loginMailAddress = LoginMailAddress(value)

        val validator = LoginMailAddress.validator
        val violations = validator.validate(loginMailAddress)

        assertEquals(1, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "plainaddress",
            "missingatsign.com",
            "@missingusername.com",
            "missing.domain.name@",
            "missing@domain@name.com",
            "spaces in@email.com",
            "email@spaces in.com",
            "email@.com",
            "email@domain.",
            ".email@domain.com",
            "email.@domain.com",
            "email@domain..com",
        ],
    )
    fun `メールアドレスの形式が不正な場合エラーとなる`(value: String) {
        val loginMailAddress = LoginMailAddress(value)

        val validator = LoginMailAddress.validator
        val violations = validator.validate(loginMailAddress)

        assertEquals(1, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.very.long.email.address@example.com",
        ],
    )
    fun `メールアドレスが254文字を超える場合エラーとなる`(value: String) {
        val loginMailAddress = LoginMailAddress(value)

        val validator = LoginMailAddress.validator
        val violations = validator.validate(loginMailAddress)

        assertEquals(1, violations.size)
    }
}
