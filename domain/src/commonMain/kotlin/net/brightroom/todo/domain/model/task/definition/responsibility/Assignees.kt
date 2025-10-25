@file:OptIn(ExperimentalUuidApi::class)

package net.brightroom.todo.domain.model.task.definition.responsibility

import net.brightroom.todo.domain.model.user.identity.UserId
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * タスクの担当者一覧
 */
data class Assignees(private val list: List<UserId> = listOf())
