package net.brightroom.todo.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class TaskId(val value: String) {
    companion object {
        fun generate(): TaskId = TaskId(UUID.randomUUID().toString())
        fun from(value: String): TaskId = TaskId(value)
    }
}

@OptIn(ExperimentalTime::class)
@Serializable
data class Task(
    val id: TaskId,
    val title: String,
    val description: String = "",
    val dueDate: LocalDate? = null,
    @Contextual val createdAt: Instant,
    val subtasks: List<SubTask> = emptyList(),
    val reminders: List<TaskReminder> = emptyList(),
    val repeatRule: TaskRepeatRule? = null,
    val tags: List<Tag> = emptyList(),
    val isCompleted: Boolean = false,
    @Contextual val completedAt: Instant? = null
) {
    init {
        require(title.isNotBlank()) { "Task title cannot be blank" }
        require(title.length <= 200) { "Task title cannot exceed 200 characters" }
        if (dueDate != null) {
            // Due date validation will be handled in domain policies
        }
    }

    fun complete(completedAt: Instant): Task {
        return copy(isCompleted = true, completedAt = completedAt)
    }

    fun uncomplete(): Task {
        return copy(isCompleted = false, completedAt = null)
    }

    fun updateTitle(newTitle: String): Task {
        require(newTitle.isNotBlank()) { "Task title cannot be blank" }
        require(newTitle.length <= 200) { "Task title cannot exceed 200 characters" }
        return copy(title = newTitle)
    }

    fun updateDescription(newDescription: String): Task {
        return copy(description = newDescription)
    }

    fun updateDueDate(newDueDate: LocalDate?): Task {
        return copy(dueDate = newDueDate)
    }

    fun addSubtask(subtask: SubTask): Task {
        return copy(subtasks = subtasks + subtask)
    }

    fun removeSubtask(subtaskId: SubTaskId): Task {
        return copy(subtasks = subtasks.filterNot { it.id == subtaskId })
    }

    fun updateSubtask(updatedSubtask: SubTask): Task {
        return copy(subtasks = subtasks.map { if (it.id == updatedSubtask.id) updatedSubtask else it })
    }

    fun addReminder(reminder: TaskReminder): Task {
        return copy(reminders = reminders + reminder)
    }

    fun removeReminder(reminderId: TaskReminderId): Task {
        return copy(reminders = reminders.filterNot { it.id == reminderId })
    }

    fun setRepeatRule(repeatRule: TaskRepeatRule?): Task {
        return copy(repeatRule = repeatRule)
    }

    fun addTag(tag: Tag): Task {
        if (tags.any { it.id == tag.id }) return this
        return copy(tags = tags + tag)
    }

    fun removeTag(tagId: TagId): Task {
        return copy(tags = tags.filterNot { it.id == tagId })
    }
}
