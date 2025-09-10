package net.brightroom.migration.executor.infrastructure

import net.brightroom.migration.detector.domain.model.table.MigrationTables
import net.brightroom.migration.executor.application.repository.MigrationStatementNotificationRepository
import org.jetbrains.exposed.v1.migration.MigrationUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class OutputLoggingMigrationStatement : MigrationStatementNotificationRepository {
    private val log = LoggerFactory.getLogger(OutputLoggingMigrationStatement::class.java)

    override fun notify(tables: MigrationTables) {
        val statements = MigrationUtils.statementsRequiredForDatabaseMigration(*tables.toArray(), withLogs = false)
        log.info(categorizedMigrationLog(statements))
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
