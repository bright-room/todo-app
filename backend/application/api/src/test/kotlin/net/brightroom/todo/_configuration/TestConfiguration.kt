package net.brightroom.todo._configuration

import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import net.brightroom.todo.infrastructure.datasource.task.CreateDummyTaskDataSource
import net.brightroom.todo.infrastructure.datasource.task.TaskClearDataSource

fun Application.testConfigure() {
    dependencies {
        provide<CreateDummyTaskDataSource> { CreateDummyTaskDataSource(resolve()) }
        provide<TaskClearDataSource> { TaskClearDataSource(resolve()) }
    }
}
