package net.brightroom.todo.domain.model.content

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("タスクのタイトルテスト")
class TitleTest {
    @ParameterizedTest
    @ValueSource(
        strings = [
            "タスクタイトル",
            "Task Title",
            "短いタイトル",
            "A",
            "日本語と英語が混在したTitle",
            "数字123を含むタイトル",
            "記号!@#$%を含むタイトル",
            "非常に長いタイトルですが200文字以内なので問題ありません。非常に長いタイトルですが200文字以内なので問題ありません。非常に長いタイトルですが200文字以内なので問題ありません。非常に長いタイトルですが200文字以内なので問題ありません。非常に長いタイトルですが200文字以内",
        ],
    )
    fun `タイトルが正常に生成できる`(value: String) {
        val title = Title(value)

        val validator = Title.validator
        val violations = validator.validate(title)

        Assertions.assertEquals(0, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
        ],
    )
    fun `タイトルが空の場合エラーとなる`(value: String) {
        val title = Title(value)

        val validator = Title.validator
        val violations = validator.validate(title)

        Assertions.assertEquals(1, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            " ",
            "　",
            "\t",
            "\n",
            "\r",
            "  \t  \n  ",
        ],
    )
    fun `タイトルが空白のみの場合エラーとなる`(value: String) {
        val title = Title(value)

        val validator = Title.validator
        val violations = validator.validate(title)

        Assertions.assertEquals(1, violations.size)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "このタイトルは200文字を超えるように作成されています。このタイトルは200文字を超えるように作成されています。このタイトルは200文字を超えるように作成されています。このタイトルは200文字を超えるように作成されています。このタイトルは200文字を超えるように作成されています。このタイトルは200文字を超えるように作成されています。このタイトルは200文字を超えるように作成されています。このタイトルは200文字を超えるように作成されています。この部分で確実に200文字を超過します。",
        ],
    )
    fun `タイトルが200文字を超える場合エラーとなる`(value: String) {
        val title = Title(value)

        val validator = Title.validator
        val violations = validator.validate(title)

        Assertions.assertEquals(1, violations.size)
    }
}