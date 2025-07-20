package net.brightroom.todo.domain.model.tag

import am.ik.yavi.builder.validator
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * タグのタイトル
 */
@JvmInline
@Serializable
value class TagTitle(private val value: String) {
    operator fun invoke(): String = value
    override fun toString(): String = value

    companion object {
        val validator = validator<TagTitle> {
            TagTitle::value {
                notBlank().message("タグタイトルが空文字です")
                greaterThanOrEqual(1).message("タグタイトルは1文字以上である必要があります")
                lessThanOrEqual(30).message("タグタイトルは30文字以下である必要があります")
            }
        }
    }
}