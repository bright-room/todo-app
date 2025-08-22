package net.brightroom.todo.domain.model.task.content

import am.ik.yavi.builder.validator
import kotlin.jvm.JvmInline

/** タスクのタイトル */
@JvmInline
value class Title(
    private val value: String,
) {
    operator fun invoke() = value

    override fun toString() = value

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
