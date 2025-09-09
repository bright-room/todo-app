package net.brightroom.migration.generator.application.repository

import org.jetbrains.exposed.v1.core.Table

interface CreateMigrationScriptRepository {
    fun create(tables: Array<Table>)
}
