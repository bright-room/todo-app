package net.brightroom.todo.domain.model.project.planning.due

import kotlinx.datetime.LocalDate
import kotlin.jvm.JvmInline

/**
 * プロジェクトの予定期限日
 */
@JvmInline
value class DueDate(private val value: LocalDate) {
    fun is予定期限日が予定開始日よりも前(beginDate: BeginDate) = value > beginDate()

    operator fun invoke(): LocalDate = value

    override fun toString(): String = value.toString()
}
