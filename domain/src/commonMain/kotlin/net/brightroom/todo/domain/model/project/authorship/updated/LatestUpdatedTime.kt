package net.brightroom.todo.domain.model.project.authorship.updated

import kotlinx.datetime.LocalDateTime
import net.brightroom._extensions.kotlinx.datetime.now
import kotlin.jvm.JvmInline

/**
 * プロジェクトの最終更新日時
 */
@JvmInline
value class LatestUpdatedTime(private val value: LocalDateTime) {
    operator fun invoke(): LocalDateTime = value

    override fun toString(): String = value.toString()

    companion object {
        fun now(): LatestUpdatedTime = LatestUpdatedTime(LocalDateTime.now())
    }
}
