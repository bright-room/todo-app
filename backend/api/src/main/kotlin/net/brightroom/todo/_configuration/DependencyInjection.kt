package net.brightroom.todo._configuration

import net.brightroom.todo.backend.api.application.repository.TaskCompleteRepository
import net.brightroom.todo.backend.api.application.repository.TaskCreateRepository
import net.brightroom.todo.backend.api.application.repository.TaskDeleteRepository
import net.brightroom.todo.backend.api.application.repository.TaskRepository
import net.brightroom.todo.backend.api.application.repository.TaskUpdateRepository
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
import org.koin.dsl.module

/**
 * 依存性注入設定
 */
val appModule =
    module {

        // Infrastructure Layer - DataSources
        single<TaskDataSource> { TaskDataSource() }
        single<TaskRepository> { get<TaskDataSource>() }
        single<TaskCreateRepository> { TaskCreateDataSource(get()) }
        single<TaskUpdateRepository> { TaskUpdateDataSource(get()) }
        single<TaskCompleteRepository> { TaskCompleteDataSource(get()) }
        single<TaskDeleteRepository> { TaskDeleteDataSource(get()) }

        // Application Layer - Services
        single { TaskQueryService(get()) }
        single { TaskCreateService(get()) }
        single { TaskUpdateService(get(), get()) }
        single { TaskCompleteService(get(), get()) }
        single { TaskDeleteService(get(), get()) }
    }
