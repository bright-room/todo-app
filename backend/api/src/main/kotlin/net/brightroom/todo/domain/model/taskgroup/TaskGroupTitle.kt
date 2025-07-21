package net.brightroom.todo.domain.model.taskgroup

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * タスクグループのタイトル
 */
@JvmInline
@Serializable
value class TaskGroupTitle(private val value: String) {
    operator fun invoke(): String = value
    override fun toString(): String = value

    companion object {
        val validator = validator<TaskGroupTitle> {
            TaskGroupTitle::value {
                notBlank().message("タスクグループタイトルが空文字です")
                greaterThanOrEqual(1).message("タスクグループタイトルは1文字以上である必要があります")
                lessThanOrEqual(100).message("タスクグループタイトルは100文字以下である必要があります")
            }
        }
    }
}