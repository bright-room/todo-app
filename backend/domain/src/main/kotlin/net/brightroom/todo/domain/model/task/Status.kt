package net.brightroom.todo.domain.model.task

enum class Status {
    未完了,
    完了,
    ;

    fun is完了済み(): Boolean = 完了 == this
}
