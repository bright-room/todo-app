package net.brightroom.todo.domain.problem

/**
 * リソースが存在しない場合の例外
 */
class ResourceNotFoundException(message: String) : RuntimeException(message)