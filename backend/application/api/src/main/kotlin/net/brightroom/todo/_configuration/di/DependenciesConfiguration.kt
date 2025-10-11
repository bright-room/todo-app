package net.brightroom.todo._configuration.di

import io.ktor.server.application.Application
import io.ktor.server.plugins.di.annotations.Property
import io.ktor.server.plugins.di.dependencies
import net.brightroom.todo._configuration.Environment
import net.brightroom.todo.application.repository.task.CreateTaskRepository
import net.brightroom.todo.application.repository.task.TaskRepository
import net.brightroom.todo.application.repository.task.content.TaskContentRegisterRepository
import net.brightroom.todo.application.repository.task.lifecycle.TaskLifecycleRegisterRepository
import net.brightroom.todo.application.repository.task.planning.TaskDueDateRegisterRepository
import net.brightroom.todo.application.repository.task.planning.TaskPriorityRegisterRepository
import net.brightroom.todo.application.scenario.task.CreateTaskScenario
import net.brightroom.todo.application.service.task.CreateTaskService
import net.brightroom.todo.application.service.task.TaskService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService
import net.brightroom.todo.application.service.task.lifecycle.TaskLifecycleRegisterService
import net.brightroom.todo.application.service.task.planning.TaskDueDateRegisterService
import net.brightroom.todo.application.service.task.planning.TaskPriorityRegisterService
import net.brightroom.todo.infrastructure.datasource.task.CreateTaskDataSource
import net.brightroom.todo.infrastructure.datasource.task.TaskDataSource
import net.brightroom.todo.infrastructure.datasource.task.content.TaskContentRegisterDataSource
import net.brightroom.todo.infrastructure.datasource.task.lifecycle.TaskLifecycleRegisterDataSource
import net.brightroom.todo.infrastructure.datasource.task.planning.TaskDueDateRegisterDataSource
import net.brightroom.todo.infrastructure.datasource.task.planning.TaskPriorityRegisterDataSource

fun Application.dependenciesConfigure(
    @Property("ktor.environment") environment: Environment,
) {
    dependencies {
        provide<CreateTaskRepository> { CreateTaskDataSource(resolve()) }
        provide<CreateTaskService> { CreateTaskService(resolve()) }

        provide<TaskContentRegisterRepository> { TaskContentRegisterDataSource(resolve()) }
        provide<TaskContentRegisterService> { TaskContentRegisterService(resolve()) }

        provide<TaskPriorityRegisterRepository> { TaskPriorityRegisterDataSource(resolve()) }
        provide<TaskPriorityRegisterService> { TaskPriorityRegisterService(resolve()) }

        provide<TaskDueDateRegisterRepository> { TaskDueDateRegisterDataSource(resolve()) }
        provide<TaskDueDateRegisterService> { TaskDueDateRegisterService(resolve()) }

        provide<TaskLifecycleRegisterRepository> { TaskLifecycleRegisterDataSource(resolve()) }
        provide<TaskLifecycleRegisterService> { TaskLifecycleRegisterService(resolve()) }

        provide<CreateTaskScenario> { CreateTaskScenario(resolve(), resolve(), resolve(), resolve()) }

        provide<TaskRepository> { TaskDataSource(resolve()) }
        provide<TaskService> { TaskService(resolve()) }
    }
}
