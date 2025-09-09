package net.brightroom.migration.generator

import net.brightroom.migration.detector.TableDiscovery
import net.brightroom.migration.generator.application.service.CreateMigrationScriptService
import net.brightroom.migration.generator.domain.model.MigrationPackage
import org.jetbrains.exposed.v1.core.ExperimentalDatabaseMigrationApi
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class Application(
    private val migratePackage: MigrationPackage,
    private val createMigrationScriptService: CreateMigrationScriptService,
) : ApplicationRunner {
    @OptIn(ExperimentalDatabaseMigrationApi::class)
    override fun run(args: ApplicationArguments?) {
        val tables = TableDiscovery.findAnnotatedTableObjects(migratePackage())
        createMigrationScriptService.create(tables)
    }
}

fun main(args: Array<String>) = runApplication<Application>(*args).close()
