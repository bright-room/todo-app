package net.brightroom.todo.domain.problem

class AlreadyCompletedException(
    message: String,
) : RuntimeException(message)
