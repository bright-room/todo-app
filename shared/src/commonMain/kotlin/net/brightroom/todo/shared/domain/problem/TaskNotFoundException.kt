package net.brightroom.todo.shared.domain.problem

/**
 * タスクが見つからない場合の例外
 */
class TaskNotFoundException(
    message: String,
) : Exception(message)
