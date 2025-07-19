package net.brightroom.todo.domain.policy

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.core.Validator
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.brightroom.todo.domain.model.Task
import net.brightroom.todo.domain.model.TaskReminder
import net.brightroom.todo.domain.model.SubTask
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
object TaskValidationPolicy {

    private val taskTitleValidator: Validator<String> = ValidatorBuilder.of<String>()
        .constraint(String::toString, "title") { it.notBlank().lessThanOrEqual(200) }
        .build()

    private val subTaskTitleValidator: Validator<String> = ValidatorBuilder.of<String>()
        .constraint(String::toString, "title") { it.notBlank().lessThanOrEqual(200) }
        .build()

    /**
     * Validates that the due date is after the task creation date
     */
    fun validateDueDate(dueDate: LocalDate?, createdAt: Instant): Boolean {
        if (dueDate == null) return true

        val createdDate = createdAt.toLocalDateTime(TimeZone.of("system")).date
        return dueDate >= createdDate
    }

    /**
     * Validates that reminder datetime is between task creation and due date
     */
    fun validateReminderDateTime(reminderDateTime: Instant, createdAt: Instant, dueDate: LocalDate?): Boolean {
        // Reminder must be after creation time
        if (reminderDateTime < createdAt) return false

        // If there's a due date, reminder should be before or on the due date
        if (dueDate != null) {
            val reminderDate = reminderDateTime.toLocalDateTime(TimeZone.of("system")).date
            if (reminderDate > dueDate) return false
        }

        return true
    }

    /**
     * Validates a complete task with all its business rules using yavi
     */
    fun validateTask(task: Task): ValidationResult {
        val errors = mutableListOf<String>()

        // Validate title using yavi
        val titleValidation = taskTitleValidator.validate(task.title)
        if (!titleValidation.isValid) {
            errors.addAll(titleValidation.map { it.message() })
        }

        // Validate due date
        if (!validateDueDate(task.dueDate, task.createdAt)) {
            errors.add("Due date must be on or after the task creation date")
        }

        // Validate reminders
        task.reminders.forEach { reminder ->
            if (!validateReminderDateTime(reminder.reminderDateTime, task.createdAt, task.dueDate)) {
                errors.add("Reminder datetime must be between task creation time and due date")
            }
        }

        // Validate subtasks using yavi
        task.subtasks.forEach { subtask ->
            val subtaskTitleValidation = subTaskTitleValidator.validate(subtask.title)
            if (!subtaskTitleValidation.isValid) {
                errors.addAll(subtaskTitleValidation.map { "SubTask: ${it.message()}" })
            }
        }

        return if (errors.isEmpty()) {
            ValidationResult.Success
        } else {
            ValidationResult.Failure(errors)
        }
    }
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Failure(val errors: List<String>) : ValidationResult()

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure
}
