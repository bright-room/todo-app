package net.brightroom.migration.detector

import net.brightroom.migration.detector.application.service.clazz.MigrationTargetDetectService
import net.brightroom.migration.detector.domain.model.clazz.ScanBasePackage
import net.brightroom.migration.detector.domain.model.table.MigrationTableFactory
import net.brightroom.migration.detector.domain.model.table.MigrationTables
import org.springframework.stereotype.Component

@Component
class MigrationDetector internal constructor(
    private val migrationTargetDetectService: MigrationTargetDetectService,
    private val scanBasePackage: ScanBasePackage,
) {
    fun detect(): MigrationTables {
        val scanClasses = migrationTargetDetectService.listAll(scanBasePackage)
        return MigrationTableFactory.create(scanClasses)
    }
}
