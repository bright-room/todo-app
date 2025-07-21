package net.brightroom.todo.backend.api

import net.brightroom.todo.backend.api.application.service.TaskCompleteService
import net.brightroom.todo.backend.api.application.service.TaskCreateService
import net.brightroom.todo.backend.api.application.service.TaskDeleteService
import net.brightroom.todo.backend.api.application.service.TaskQueryService
import net.brightroom.todo.backend.api.application.service.TaskUpdateService
import net.brightroom.todo.backend.api.infrastructure.datasource.TaskCompleteDataSource
import net.brightroom.todo.backend.api.infrastructure.datasource.TaskCreateDataSource
import net.brightroom.todo.backend.api.infrastructure.datasource.TaskDataSource
import net.brightroom.todo.backend.api.infrastructure.datasource.TaskDeleteDataSource
import net.brightroom.todo.backend.api.infrastructure.datasource.TaskUpdateDataSource

/**
 * Bright Room Todo API アプリケーション
 *
 * Note: This is a placeholder implementation.
 * The actual Ktor server setup will be implemented when dependencies are properly resolved.
 */
fun main() {
    println("Bright Room Todo API Server")
    println("Starting application...")

    // Initialize dependencies manually for demonstration
    val taskDataSource = TaskDataSource()
    val taskCreateDataSource = TaskCreateDataSource(taskDataSource)
    val taskUpdateDataSource = TaskUpdateDataSource(taskDataSource)
    val taskCompleteDataSource = TaskCompleteDataSource(taskDataSource)
    val taskDeleteDataSource = TaskDeleteDataSource(taskDataSource)

    val taskQueryService = TaskQueryService(taskDataSource)
    val taskCreateService = TaskCreateService(taskCreateDataSource)
    val taskUpdateService = TaskUpdateService(taskDataSource, taskUpdateDataSource)
    val taskCompleteService = TaskCompleteService(taskDataSource, taskCompleteDataSource)
    val taskDeleteService = TaskDeleteService(taskDataSource, taskDeleteDataSource)

    println("Services initialized successfully")
    println("Server would start on port 8080")
    println("Available endpoints:")
    println("  GET  /api/v1/tasks - Get all tasks")
    println("  GET  /api/v1/tasks/{id} - Get task by ID")
    println("  GET  /api/v1/tasks/completed - Get completed tasks")
    println("  GET  /api/v1/tasks/incomplete - Get incomplete tasks")
    println("  GET  /api/v1/tasks/overdue - Get overdue tasks")
    println("  POST /api/v1/tasks/create - Create new task")
    println("  POST /api/v1/tasks/update - Update task")
    println("  POST /api/v1/tasks/complete - Complete task")
    println("  POST /api/v1/tasks/uncomplete - Uncomplete task")
    println("  POST /api/v1/tasks/delete - Delete task")

    // Keep the application running
    println("Application is ready. Press Ctrl+C to stop.")
    readlnOrNull()
}
