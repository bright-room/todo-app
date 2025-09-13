package net.brightroom.todo._configuration.di

import io.ktor.server.application.Application
import io.ktor.server.plugins.di.annotations.Property
import io.ktor.server.plugins.di.dependencies
import net.brightroom.todo.application.repository.task.CreateTaskRegisterRepository
import net.brightroom.todo.application.repository.task.classification.TaskClassificationRegisterRepository
import net.brightroom.todo.application.repository.task.content.TaskContentRegisterRepository
import net.brightroom.todo.application.repository.task.lifecycle.TaskLifecycleRegisterRepository
import net.brightroom.todo.application.repository.task.planning.TaskPlanningRegisterRepository
import net.brightroom.todo.application.scenario.task.TaskRegisterScenario
import net.brightroom.todo.application.service.task.CreateTaskRegisterService
import net.brightroom.todo.application.service.task.classification.TaskClassificationRegisterService
import net.brightroom.todo.application.service.task.content.TaskContentRegisterService
import net.brightroom.todo.application.service.task.lifecycle.TaskLifecycleRegisterService
import net.brightroom.todo.application.service.task.planning.TaskPlanningRegisterService
import net.brightroom.todo.infrastructure.datasource.task.CreateTaskRegisterDataSource
import net.brightroom.todo.infrastructure.datasource.task.classification.TaskClassificationRegisterDataSource
import net.brightroom.todo.infrastructure.datasource.task.classification.tag.TagRegisterDataSource
import net.brightroom.todo.infrastructure.datasource.task.classification.tag.TagRegisterRepository
import net.brightroom.todo.infrastructure.datasource.task.content.TaskContentRegisterDataSource
import net.brightroom.todo.infrastructure.datasource.task.lifecycle.TaskLifecycleRegisterDataSource
import net.brightroom.todo.infrastructure.datasource.task.planning.TaskPlanningRegisterDataSource

fun Application.configure(
    @Property("ktor.development") isDevelopment: Boolean,
) {
    dependencies {
        provide<CreateTaskRegisterRepository> { CreateTaskRegisterDataSource(resolve()) }
        provide<CreateTaskRegisterService> { CreateTaskRegisterService(resolve()) }

        provide<TaskContentRegisterRepository> { TaskContentRegisterDataSource(resolve()) }
        provide<TaskContentRegisterService> { TaskContentRegisterService(resolve()) }

        provide<TaskPlanningRegisterRepository> { TaskPlanningRegisterDataSource(resolve()) }
        provide<TaskPlanningRegisterService> { TaskPlanningRegisterService(resolve()) }

        provide<TaskLifecycleRegisterRepository> { TaskLifecycleRegisterDataSource(resolve()) }
        provide<TaskLifecycleRegisterService> { TaskLifecycleRegisterService(resolve()) }

        provide<TagRegisterRepository> { TagRegisterDataSource(resolve()) }

        provide<TaskClassificationRegisterRepository> { TaskClassificationRegisterDataSource(resolve(), resolve()) }
        provide<TaskClassificationRegisterService> { TaskClassificationRegisterService(resolve()) }

        provide<TaskRegisterScenario> { TaskRegisterScenario(resolve(), resolve(), resolve(), resolve(), resolve()) }
    }
}
