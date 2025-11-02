package net.brightroom.todo.domain.model.task.lifecycle.transition

/**
 * タスクの遷移状態
 */
sealed interface Transition {
    object None : Transition

    data class Started(private val beganDate: BeganDate) : Transition

    data class Completed(private val beganDate: BeganDate, private val completedDate: CompletedDate) : Transition

    companion object {
        fun of(
            beganDate: BeganDate? = null,
            completedDate: CompletedDate? = null,
        ) = when {
            beganDate != null && completedDate != null -> Completed(beganDate, completedDate)
            beganDate != null -> Started(beganDate)
            else -> None
        }
    }
}
