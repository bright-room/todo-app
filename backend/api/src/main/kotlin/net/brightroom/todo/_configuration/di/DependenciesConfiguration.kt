package net.brightroom.todo._configuration.di

import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.plugins.di.resolve
import net.brightroom.todo.application.repository.task.TaskCompleteRepository
import net.brightroom.todo.application.repository.task.TaskCreateRepository
import net.brightroom.todo.application.repository.task.TaskRepository
import net.brightroom.todo.application.repository.task.content.TaskContentRegisterRepository
import net.brightroom.todo.application.service.task.TaskCompleteService
import net.brightroom.todo.application.service.task.TaskCreateService
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService
import net.brightroom.todo.infrastructure.datasource.task.TaskCompleteDataSource
import net.brightroom.todo.infrastructure.datasource.task.TaskCreateDataSource
import net.brightroom.todo.infrastructure.datasource.task.TaskDataSource
import net.brightroom.todo.infrastructure.datasource.task.content.TaskContentRegisterDataSource

fun Application.configure() {
    dependencies {
        provide<TaskRepository> { TaskDataSource(resolve()) }
        provide<TaskCreateRepository> { TaskCreateDataSource(resolve()) }
        provide<TaskContentRegisterRepository> { TaskContentRegisterDataSource(resolve()) }
        provide<TaskCompleteRepository> { TaskCompleteDataSource(resolve()) }

        provide<TaskService> { TaskService(resolve()) }
        provide<TaskCreateService> { TaskCreateService(resolve()) }
        provide<TaskContentRegisterService> { TaskContentRegisterService(resolve()) }
        provide<TaskCompleteService> { TaskCompleteService(resolve()) }
    }
}
