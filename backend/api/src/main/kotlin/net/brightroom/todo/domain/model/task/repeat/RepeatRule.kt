package net.brightroom.todo.domain.model.task.repeat

import kotlinx.serialization.Serializable

/**
 * 繰り返しルール
 */
@Serializable
enum class RepeatRule {
    繰り返しなし,
    毎日,
    平日のみ,
    休日のみ,
    週単位,
    月単位,
    年単位;

    fun is繰り返しなし(): Boolean = this == 繰り返しなし
    fun is週単位(): Boolean = this == 週単位
    fun is月単位(): Boolean = this == 月単位
    fun is年単位(): Boolean = this == 年単位
}