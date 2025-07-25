package net.brightroom.todo.domain.model.task

import kotlinx.serialization.Serializable

/**
 * タスク一覧
 */
@Serializable
data class Tasks(private val list: List<Task>) {
    operator fun invoke(): List<Task> = list
    override fun toString(): String = list.toString()
}