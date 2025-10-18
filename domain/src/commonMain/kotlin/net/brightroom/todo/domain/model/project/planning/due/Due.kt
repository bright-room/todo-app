package net.brightroom.todo.domain.model.project.planning.due

/**
 * プロジェクトの予定期限
 */
sealed interface Due {
    data class Set(private val beginDate: BeginDate, private val dueDate: DueDate) : Due {
        init {
            require(dueDate.is予定期限日が予定開始日よりも前(beginDate)) { "The due date must be before the start date" }
        }
    }

    object Unset : Due

    companion object {
        fun of(
            beginDate: BeginDate? = null,
            dueDate: DueDate? = null,
        ) = when {
            beginDate == null || dueDate == null -> Unset
            else -> Set(beginDate, dueDate)
        }
    }
}
