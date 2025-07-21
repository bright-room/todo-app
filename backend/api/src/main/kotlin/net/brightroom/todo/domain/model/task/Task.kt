package net.brightroom.todo.domain.model.task

import am.ik.yavi.builder.validator
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import net.brightroom.todo.domain.model.task.duedate.DaysUntilDue
import net.brightroom.todo.domain.model.task.duedate.DueDate
import net.brightroom.todo.domain.model.task.duedate.SetDueDate

/**
 * タスク集約
 */
@Serializable
data class Task(
    val id: TaskId,
    val title: Title,
    val description: Description = Description(""),
    val dueDate: DueDate
) {

    fun toDaysUntilDue(today: LocalDate): DaysUntilDue {
        if (!dueDate.is期限日セット済み()) {
            return DaysUntilDue(Long.MAX_VALUE) // 期日が設定されていない場合
        }

        return DaysUntilDue(dueDate().toEpochDays() - today.toEpochDays())
    }

    fun is期限超過(today: LocalDate): Boolean = toDaysUntilDue(today).is期限超過()
    fun is期限当日(today: LocalDate): Boolean = toDaysUntilDue(today).is期限当日()
    fun isDueSoon(today: LocalDate, threshold: Long = 3): Boolean = toDaysUntilDue(today).isDueSoon(threshold)
}
