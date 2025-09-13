package net.brightroom.todo.domain.model.identity

/**
 * タスクを一意に表す識別子
 */
data class Identity(
    val id: Id,
    val createdTime: CreatedTime,
)
