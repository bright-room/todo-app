package net.brightroom.todo.application.service

import net.brightroom.todo.application.repository.TaskRepository
import net.brightroom.todo.application.repository.SubTaskRepository
import net.brightroom.todo.application.repository.TagRepository
import net.brightroom.todo.domain.model.*
import net.brightroom.todo.domain.policy.TaskValidationPolicy
import net.brightroom.todo.domain.policy.ValidationResult
import net.brightroom.todo.domain.problem.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class TaskService(
    private val taskRepository: TaskRepository,
    private val subTaskRepository: SubTaskRepository,
    private val tagRepository: TagRepository
) {

    suspend fun createTask(
        title: String,
        description: String = "",
        dueDate: kotlinx.datetime.LocalDate? = null,
        reminders: List<TaskReminder> = emptyList(),
        repeatRule: TaskRepeatRule? = null,
        tagIds: List<TagId> = emptyList()
    ): Task {
        val now = Clock.System.now()
        val taskId = TaskId.generate()

        // Fetch tags
        val tags = tagIds.mapNotNull { tagRepository.findById(it) }

        val task = Task(
            id = taskId,
            title = title,
            description = description,
            dueDate = dueDate,
            createdAt = now,
            reminders = reminders,
            repeatRule = repeatRule,
            tags = tags
        )

        // Validate task
        when (val validationResult = TaskValidationPolicy.validateTask(task)) {
            is ValidationResult.Failure -> throw TaskValidationException(validationResult.errors)
            ValidationResult.Success -> { /* Continue */ }
        }

        return taskRepository.save(task)
    }

    suspend fun getTask(taskId: TaskId): Task {
        return taskRepository.findById(taskId) ?: throw TaskNotFoundException(taskId)
    }

    suspend fun getAllTasks(): List<Task> {
        return taskRepository.findAll()
    }

    suspend fun getCompletedTasks(): List<Task> {
        return taskRepository.findByCompleted(true)
    }

    suspend fun getIncompleteTasks(): List<Task> {
        return taskRepository.findByCompleted(false)
    }

    suspend fun updateTask(
        taskId: TaskId,
        title: String? = null,
        description: String? = null,
        dueDate: kotlinx.datetime.LocalDate? = null
    ): Task {
        val existingTask = taskRepository.findById(taskId) ?: throw TaskNotFoundException(taskId)

        val updatedTask = existingTask.copy(
            title = title ?: existingTask.title,
            description = description ?: existingTask.description,
            dueDate = dueDate ?: existingTask.dueDate
        )

        // Validate updated task
        when (val validationResult = TaskValidationPolicy.validateTask(updatedTask)) {
            is ValidationResult.Failure -> throw TaskValidationException(validationResult.errors)
            ValidationResult.Success -> { /* Continue */ }
        }

        return taskRepository.update(updatedTask)
    }

    suspend fun completeTask(taskId: TaskId): Task {
        val task = taskRepository.findById(taskId) ?: throw TaskNotFoundException(taskId)

        if (task.isCompleted) {
            throw TaskAlreadyCompletedException(taskId)
        }

        val completedTask = task.complete(Clock.System.now())
        return taskRepository.update(completedTask)
    }

    suspend fun uncompleteTask(taskId: TaskId): Task {
        val task = taskRepository.findById(taskId) ?: throw TaskNotFoundException(taskId)

        if (!task.isCompleted) {
            throw TaskNotCompletedException(taskId)
        }

        val uncompletedTask = task.uncomplete()
        return taskRepository.update(uncompletedTask)
    }

    suspend fun deleteTask(taskId: TaskId): Boolean {
        if (!taskRepository.existsById(taskId)) {
            throw TaskNotFoundException(taskId)
        }

        // Delete related subtasks
        subTaskRepository.deleteByTaskId(taskId)

        return taskRepository.delete(taskId)
    }

    suspend fun addTagToTask(taskId: TaskId, tagId: TagId): Task {
        val task = taskRepository.findById(taskId) ?: throw TaskNotFoundException(taskId)
        val tag = tagRepository.findById(tagId) ?: throw TagNotFoundException(tagId)

        val updatedTask = task.addTag(tag)
        tagRepository.addTagToTask(tagId, taskId)

        return taskRepository.update(updatedTask)
    }

    suspend fun removeTagFromTask(taskId: TaskId, tagId: TagId): Task {
        val task = taskRepository.findById(taskId) ?: throw TaskNotFoundException(taskId)

        val updatedTask = task.removeTag(tagId)
        tagRepository.removeTagFromTask(tagId, taskId)

        return taskRepository.update(updatedTask)
    }

    suspend fun addReminderToTask(taskId: TaskId, reminderDateTime: Instant): Task {
        val task = taskRepository.findById(taskId) ?: throw TaskNotFoundException(taskId)

        // Validate reminder
        if (!TaskValidationPolicy.validateReminderDateTime(reminderDateTime, task.createdAt, task.dueDate)) {
            throw InvalidReminderException("Reminder datetime must be between task creation time and due date")
        }

        val reminder = TaskReminder(
            id = TaskReminderId.generate(),
            taskId = taskId,
            reminderDateTime = reminderDateTime
        )

        val updatedTask = task.addReminder(reminder)
        return taskRepository.update(updatedTask)
    }

    suspend fun removeReminderFromTask(taskId: TaskId, reminderId: TaskReminderId): Task {
        val task = taskRepository.findById(taskId) ?: throw TaskNotFoundException(taskId)

        val updatedTask = task.removeReminder(reminderId)
        return taskRepository.update(updatedTask)
    }
}
