package net.brightroom.todo.domain.model.project.lifecycle.transition

import kotlinx.datetime.LocalDate
import kotlin.jvm.JvmInline

/**
 * プロジェクトの開始日
 */
@JvmInline
value class BeganDate(private val value: LocalDate) {
    operator fun invoke(): LocalDate = value

    override fun toString(): String = value.toString()
}
