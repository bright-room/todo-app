package net.brightroom.todo.domain.problem

import net.brightroom.todo.domain.model.TaskId
import net.brightroom.todo.domain.model.SubTaskId
import net.brightroom.todo.domain.model.TaskGroupId
import net.brightroom.todo.domain.model.TagId

/**
 * Base class for all domain-specific exceptions
 */
abstract class DomainException(message: String, cause: Throwable? = null) : Exception(message, cause)

/**
 * Task-related domain exceptions
 */
class TaskNotFoundException(taskId: TaskId) : DomainException("Task not found: ${taskId.value}")

class TaskValidationException(val errors: List<String>) : DomainException("Task validation failed: ${errors.joinToString(", ")}")

class TaskAlreadyCompletedException(taskId: TaskId) : DomainException("Task is already completed: ${taskId.value}")

class TaskNotCompletedException(taskId: TaskId) : DomainException("Task is not completed: ${taskId.value}")

/**
 * SubTask-related domain exceptions
 */
class SubTaskNotFoundException(subTaskId: SubTaskId) : DomainException("SubTask not found: ${subTaskId.value}")

class SubTaskAlreadyCompletedException(subTaskId: SubTaskId) : DomainException("SubTask is already completed: ${subTaskId.value}")

class SubTaskNotCompletedException(subTaskId: SubTaskId) : DomainException("SubTask is not completed: ${subTaskId.value}")

/**
 * TaskGroup-related domain exceptions
 */
class TaskGroupNotFoundException(taskGroupId: TaskGroupId) : DomainException("TaskGroup not found: ${taskGroupId.value}")

class TaskGroupValidationException(val errors: List<String>) : DomainException("TaskGroup validation failed: ${errors.joinToString(", ")}")

/**
 * Tag-related domain exceptions
 */
class TagNotFoundException(tagId: TagId) : DomainException("Tag not found: ${tagId.value}")

class TagValidationException(val errors: List<String>) : DomainException("Tag validation failed: ${errors.joinToString(", ")}")

class TagAlreadyExistsException(title: String) : DomainException("Tag with title already exists: $title")

/**
 * General validation exceptions
 */
class InvalidDateRangeException(message: String) : DomainException("Invalid date range: $message")

class InvalidRepeatRuleException(message: String) : DomainException("Invalid repeat rule: $message")

class InvalidReminderException(message: String) : DomainException("Invalid reminder: $message")
