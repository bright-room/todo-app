package net.brightroom.migration.executor

import net.brightroom.migration.detector.MigrationDetector
import net.brightroom.migration.executor.application.service.MigrationExecuteService
import net.brightroom.migration.executor.application.service.MigrationStatementNotificationService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.Transactional

@SpringBootApplication(scanBasePackages = ["net.brightroom.migration"])
@ConfigurationPropertiesScan
@Transactional
class Application(
    private val migrationExecuteService: MigrationExecuteService,
    private val migrationNotificationService: MigrationStatementNotificationService,
    private val migrationDetector: MigrationDetector,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val tables = migrationDetector.detect()
        migrationNotificationService.notify(tables)

        migrationExecuteService.execute()
    }
}

fun main(args: Array<String>) = runApplication<Application>(*args).close()
