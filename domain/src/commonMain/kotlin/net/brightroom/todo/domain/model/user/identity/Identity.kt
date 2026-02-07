package net.brightroom.todo.domain.model.user.identity

/**
 * ユーザーの識別子
 */
data class Identity(private val userId: UserId, private val loginMailAddress: LoginMailAddress)
