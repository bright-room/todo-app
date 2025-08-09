package net.brightroom.todo.domain.model.account.identity

data class Identity(
    val accountId: AccountId,
    val loginMailAddress: LoginMailAddress,
)
