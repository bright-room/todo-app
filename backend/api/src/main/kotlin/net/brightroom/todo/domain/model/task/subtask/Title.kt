package net.brightroom.todo.domain.model.task.subtask

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * タスクのタイトル
 */
@JvmInline
@Serializable
value class Title(private val value: String) {
    operator fun invoke(): String = value
    override fun toString(): String = value

    companion object {
        val validator = validator<Title> {
            Title::value {
                notBlank().message("タスクタイトルが空文字です")
                greaterThanOrEqual(1).message("タスクタイトルは1文字以上である必要があります")
                lessThanOrEqual(200).message("タスクタイトルは200文字以下である必要があります")
            }
        }
    }
}
