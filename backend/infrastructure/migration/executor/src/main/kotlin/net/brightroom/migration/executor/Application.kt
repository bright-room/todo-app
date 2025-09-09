package net.brightroom.migration.executor

import net.brightroom.migration.detector.TableDiscovery
import net.brightroom.migration.executor.domain.model.MigrationPackage
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.v1.migration.MigrationUtils
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.Transactional

@SpringBootApplication
@ConfigurationPropertiesScan
@Transactional
class Application(
    private val flyway: Flyway,
    private val migratePackage: MigrationPackage,
) : ApplicationRunner {
    private val log = LoggerFactory.getLogger(Application::class.java)

    override fun run(args: ApplicationArguments?) {
        val tables = TableDiscovery.findAnnotatedTableObjects(migratePackage())

        val statements = MigrationUtils.statementsRequiredForDatabaseMigration(*tables, withLogs = false)
        log.info(categorizedMigrationLog(statements))

        flyway.migrate()
    }

    private fun categorizedMigrationLog(statements: List<String>): String =
        StringBuilder()
            .apply {
                val creates = statements.filter { it.trim().startsWith("CREATE", ignoreCase = true) }
                val alters = statements.filter { it.trim().startsWith("ALTER", ignoreCase = true) }
                val drops = statements.filter { it.trim().startsWith("DROP", ignoreCase = true) }
                val others =
                    statements.filter { stmt ->
                        !creates.contains(stmt) && !alters.contains(stmt) && !drops.contains(stmt)
                    }

                appendLine("The following SQL statements are required to align the current database schema.")

                appendLine("=== Migration Summary ===")
                appendLine("Total statements: ${statements.size}")

                if (creates.isNotEmpty()) {
                    appendLine("\nCREATE statements (${creates.size}):")
                    creates.forEach { appendLine("  $it") }
                }

                if (alters.isNotEmpty()) {
                    appendLine("\nALTER statements (${alters.size}):")
                    alters.forEach { appendLine("  $it") }
                }

                if (drops.isNotEmpty()) {
                    appendLine("\nDROP statements (${drops.size}):")
                    drops.forEach { appendLine("  $it") }
                }

                if (others.isNotEmpty()) {
                    appendLine("\nOther statements (${others.size}):")
                    others.forEach { appendLine("  $it") }
                }

                appendLine("=== End Summary ===")
            }.toString()
}

fun main(args: Array<String>) = runApplication<Application>(*args).close()
