package net.brightroom.todo.domain.problem

class ResourceNotFoundException(
    message: String,
) : RuntimeException(message)
