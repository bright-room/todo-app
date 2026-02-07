package net.brightroom.todo.domain.model.project.authorship.created

import kotlinx.datetime.LocalDateTime
import net.brightroom._extensions.kotlinx.datetime.now
import kotlin.jvm.JvmInline

/**
 * プロジェクトの作成日時
 */
@JvmInline
value class CreatedTime(private val value: LocalDateTime) {
    operator fun invoke(): LocalDateTime = value

    override fun toString(): String = value.toString()

    companion object {
        fun now(): CreatedTime = CreatedTime(LocalDateTime.now())
    }
}
