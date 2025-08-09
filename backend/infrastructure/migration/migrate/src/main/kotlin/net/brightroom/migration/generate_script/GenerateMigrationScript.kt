package net.brightroom.migration.generate_script

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.alternativeParsing
import net.brightroom._extensions.kotlinx.datetime.now
import net.brightroom.migration.TableDiscovery
import org.jetbrains.exposed.v1.core.ExperimentalDatabaseMigrationApi
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.migration.MigrationUtils
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["net.brightroom.migration._configuration"])
@ConfigurationPropertiesScan(basePackages = ["net.brightroom.migration._configuration"])
class GenerateMigrationScript(
    private val database: Database,
    private val migratePackage: String,
    private val outputDirectory: String,
    private val filenameSuffix: String,
) : ApplicationRunner {
    @OptIn(ExperimentalDatabaseMigrationApi::class)
    override fun run(args: ApplicationArguments?) {
        val tables = TableDiscovery.findAnnotatedTableObjects(migratePackage)

        val now = LocalDateTime.now()
        val format =
            LocalDateTime.Format {
                year()
                monthNumber()
                day()
                hour()
                minute()
                second()
                alternativeParsing({}) {
                    secondFraction(3)
                }
            }
        val version = "V${now.format(format)}"
        val scriptName = "${version}__$filenameSuffix"

        transaction(database) {
            MigrationUtils.generateMigrationScript(
                *tables,
                scriptDirectory = outputDirectory,
                scriptName = scriptName,
                withLogs = false,
            )
        }
    }
}

fun main(args: Array<String>) = runApplication<GenerateMigrationScript>(*args).close()
