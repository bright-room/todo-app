package net.brightroom.migration.generator

import net.brightroom.migration.detector.MigrationDetector
import net.brightroom.migration.generator.application.service.CreateMigrationScriptService
import org.jetbrains.exposed.v1.core.ExperimentalDatabaseMigrationApi
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["net.brightroom.migration"])
@ConfigurationPropertiesScan
class Application(
    private val migrationDetector: MigrationDetector,
    private val createMigrationScriptService: CreateMigrationScriptService,
) : ApplicationRunner {
    @OptIn(ExperimentalDatabaseMigrationApi::class)
    override fun run(args: ApplicationArguments) {
        val tables = migrationDetector.detect()
        createMigrationScriptService.create(tables)
    }
}

fun main(args: Array<String>) = runApplication<Application>(*args).close()
