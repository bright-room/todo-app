package net.brightroom.todo.domain.model.identity

import kotlinx.serialization.Serializable

/**
 * タスクを一意に表す識別子
 */
@Serializable
data class Identity(
    val id: Id,
    val createdTime: CreatedTime,
)
