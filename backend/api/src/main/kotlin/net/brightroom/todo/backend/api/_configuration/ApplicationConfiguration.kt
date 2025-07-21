package net.brightroom.todo.backend.api._configuration

import net.brightroom.todo.backend.api.application.service.TaskCompleteService
import net.brightroom.todo.backend.api.application.service.TaskCreateService
import net.brightroom.todo.backend.api.application.service.TaskDeleteService
import net.brightroom.todo.backend.api.application.service.TaskQueryService
import net.brightroom.todo.backend.api.application.service.TaskUpdateService

/**
 * Ktorアプリケーション設定
 *
 * Note: This is a simplified configuration.
 * The actual Ktor plugins will be configured when dependencies are properly resolved.
 */
fun configureApplication() {
    // Placeholder for Ktor application configuration
    // This will be implemented when Ktor dependencies are available
}

/**
 * ルーティング設定
 *
 * Note: This is a placeholder implementation.
 * The actual routing will be configured when Ktor dependencies are available.
 */
fun configureRouting(
    taskQueryService: TaskQueryService,
    taskCreateService: TaskCreateService,
    taskUpdateService: TaskUpdateService,
    taskCompleteService: TaskCompleteService,
    taskDeleteService: TaskDeleteService,
) {
    // Placeholder for routing configuration
    // This will be implemented when Ktor dependencies are available
}
