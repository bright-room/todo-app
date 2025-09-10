package net.brightroom.migration.detector.domain.model.table

import net.brightroom.migration.annotation.Migratable
import net.brightroom.migration.detector.domain.model.clazz.ScanClasses
import org.jetbrains.exposed.v1.core.Table

/**
 * マイグレーションテーブルの生成
 */
internal object MigrationTableFactory {
    fun create(scanClasses: ScanClasses): MigrationTables {
        val extractedScanClasses =
            scanClasses
                .extract {
                    it.hasAnnotation(Migratable::class) && it.hasSuperclass(Table::class)
                }

        val tables =
            extractedScanClasses()
                .mapNotNull {
                    runCatching { it.toInstance<Table>() }
                        .getOrNull()
                }

        return MigrationTables(tables)
    }
}
