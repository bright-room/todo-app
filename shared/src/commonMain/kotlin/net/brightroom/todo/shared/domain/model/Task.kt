package net.brightroom.todo.shared.domain.model

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * タスク集約
 */
@Serializable
data class Task(
    val id: TaskId,
    val title: Title,
    val description: Description = Description.empty(),
    val dueDate: DueDate,
    val isCompleted: Boolean = false,
) {
    /**
     * 期日までの日数を計算する
     */
    @OptIn(ExperimentalTime::class)
    fun toDaysUntilDue(): Int {
        val today =
            Clock.System
                .now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date
        val dueDateValue = dueDate.toLocalDate()
        return dueDateValue.toEpochDays().toInt() - today.toEpochDays().toInt()
    }

    /**
     * タスクが期限切れかどうかを判定する
     */
    fun isOverdue(): Boolean = toDaysUntilDue() < 0 && !isCompleted

    /**
     * タスクを完了状態にする
     */
    fun complete(): Task = copy(isCompleted = true)

    /**
     * タスクを未完了状態にする
     */
    fun uncomplete(): Task = copy(isCompleted = false)

    /**
     * タスクの内容を更新する
     */
    fun update(
        title: Title? = null,
        description: Description? = null,
        dueDate: DueDate? = null,
    ): Task =
        copy(
            title = title ?: this.title,
            description = description ?: this.description,
            dueDate = dueDate ?: this.dueDate,
        )
}
