package net.brightroom.migration.executor.application.service

import net.brightroom.migration.executor.application.repository.MigrationExecuteRepository
import org.springframework.stereotype.Service

@Service
class MigrationExecuteService(
    private val migrationExecuteRepository: MigrationExecuteRepository,
) {
    fun execute() = migrationExecuteRepository.execute()
}
