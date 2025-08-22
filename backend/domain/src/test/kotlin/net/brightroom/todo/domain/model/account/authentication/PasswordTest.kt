package net.brightroom.todo.domain.model.account.authentication

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("パスワードテスト")
class PasswordTest {
    @ParameterizedTest
    @ValueSource(
        strings = [
            $$"aB1!cD2@eF3#gH4$Lws7",
            $$"aBcD1234!@#$eFgH",
        ],
    )
    fun `パスワードが正常に生成できる`(value: String) {
        val password = Password(value)

        val validator = Password.validator
        val violations = validator.validate(password)

        assertEquals(0, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
        ],
    )
    fun `パスワードが空の場合エラーとなる`(value: String) {
        val password = Password(value)

        val validator = Password.validator
        val violations = validator.validate(password)

        assertEquals(7, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "SixteenCharPa1!",
        ],
    )
    fun `パスワードが15文字以下の場合エラーとなる`(value: String) {
        val password = Password(value)

        val validator = Password.validator
        val violations = validator.validate(password)

        assertEquals(1, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "VeryLongPassword1!@#$",
        ],
    )
    fun `パスワードが21文字以上の場合エラーとなる`(value: String) {
        val password = Password(value)

        val validator = Password.validator
        val violations = validator.validate(password)

        assertEquals(1, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "ValidPass123!あ",
            "ValidPass123!😀",
            "ValidPass123!～",
            "ValidPass123!\t",
        ],
    )
    fun `パスワードに利用できない文字が含まれていない場合エラーとなる`(value: String) {
        val password = Password(value)

        val validator = Password.validator
        val violations = validator.validate(password)

        assertEquals(2, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "validpassword123!",
        ],
    )
    fun `パスワードに大文字英字が含まれていない場合エラーとなる`(value: String) {
        val password = Password(value)

        val validator = Password.validator
        val violations = validator.validate(password)

        assertEquals(1, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "VALIDPASSWORD123!",
        ],
    )
    fun `パスワードに小文字英字が含まれていない場合エラーとなる`(value: String) {
        val password = Password(value)

        val validator = Password.validator
        val violations = validator.validate(password)

        assertEquals(1, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "ValidPassword!!!",
        ],
    )
    fun `パスワードに数字が含まれていない場合エラーとなる`(value: String) {
        val password = Password(value)

        val validator = Password.validator
        val violations = validator.validate(password)

        assertEquals(1, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "ValidPassword123",
        ],
    )
    fun `パスワードに記号が含まれていない場合エラーとなる`(value: String) {
        val password = Password(value)

        val validator = Password.validator
        val violations = validator.validate(password)

        assertEquals(1, violations.size)
    }
}
