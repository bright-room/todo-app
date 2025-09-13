package net.brightroom.todo.domain.model.lifecycle

import kotlinx.serialization.Serializable

/**
 * ステータス
 */
@Serializable
enum class Status {
    完了,
    未完了,
    ;

    fun is完了済み(): Boolean = this == 完了
}
