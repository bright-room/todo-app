package net.brightroom.migration.detector._configuration

import net.brightroom.migration.detector.domain.model.clazz.ScanBasePackage
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MigrationDetectorConfiguration(
    @param:Value($$"${migration.scan-base-package}") private val scanBasePackage: String,
) {
    @Bean
    fun scanBasePackage(): ScanBasePackage = ScanBasePackage(scanBasePackage)
}
