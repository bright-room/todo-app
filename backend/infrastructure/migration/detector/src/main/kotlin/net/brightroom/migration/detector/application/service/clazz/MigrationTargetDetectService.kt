package net.brightroom.migration.detector.application.service.clazz

import net.brightroom.migration.detector.application.repository.clazz.MigrationTargetDetectRepository
import net.brightroom.migration.detector.domain.model.clazz.ScanBasePackage
import net.brightroom.migration.detector.domain.model.clazz.ScanClasses
import org.springframework.stereotype.Service

@Service
internal class MigrationTargetDetectService(private val migrationTargetDetectRepositories: List<MigrationTargetDetectRepository>) {
    fun listAll(scanBasePackage: ScanBasePackage): ScanClasses =
        migrationTargetDetectRepositories
            .map { it.listAll(scanBasePackage) }
            .fold(ScanClasses.empty()) { scanClasses, additional ->
                scanClasses.merge(additional)
            }
}
