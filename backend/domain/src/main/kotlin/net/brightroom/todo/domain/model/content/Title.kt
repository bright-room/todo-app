package net.brightroom.todo.domain.model.content

import am.ik.yavi.builder.validator

/**
 * タイトル
 */
@JvmInline
value class Title(
    private val value: String,
) {
    operator fun invoke(): String = value

    override fun toString(): String = value

    companion object {
        val validator =
            validator {
                (Title::value)("タスクのタイトル") {
                    notBlank().message("タスクのタイトルが空")
                    lessThanOrEqual(200).message("タスクのタイトルは200文字以下")
                }
            }
    }
}
