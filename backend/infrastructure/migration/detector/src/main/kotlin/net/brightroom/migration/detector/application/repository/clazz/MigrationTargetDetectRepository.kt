package net.brightroom.migration.detector.application.repository.clazz

import net.brightroom.migration.detector.domain.model.clazz.ScanBasePackage
import net.brightroom.migration.detector.domain.model.clazz.ScanClasses

interface MigrationTargetDetectRepository {
    fun listAll(scanBasePackage: ScanBasePackage): ScanClasses
}
