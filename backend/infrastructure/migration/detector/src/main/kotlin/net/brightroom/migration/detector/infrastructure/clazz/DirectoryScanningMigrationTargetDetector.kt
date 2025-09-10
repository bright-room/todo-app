package net.brightroom.migration.detector.infrastructure.clazz

import net.brightroom.migration.detector.application.repository.clazz.MigrationTargetDetectRepository
import net.brightroom.migration.detector.domain.model.clazz.ScanBasePackage
import net.brightroom.migration.detector.domain.model.clazz.ScanClass
import net.brightroom.migration.detector.domain.model.clazz.ScanClasses
import org.springframework.stereotype.Repository
import java.io.File

@Repository
internal class DirectoryScanningMigrationTargetDetector : MigrationTargetDetectRepository {
    override fun listAll(scanBasePackage: ScanBasePackage): ScanClasses {
        val classLoader = Thread.currentThread().contextClassLoader
        val path = scanBasePackage.toPath()
        val resources = classLoader.getResources(path)

        val classes = mutableListOf<ScanClass>()

        while (resources.hasMoreElements()) {
            val resource = resources.nextElement()
            if (resource.protocol == "file") {
                val dir = File(resource.file)
                val scanClasses = findClassesInDirectory(dir, scanBasePackage)
                classes.addAll(scanClasses())
            }
        }
        return ScanClasses(classes)
    }

    private fun findClassesInDirectory(
        directory: File,
        scanBasePackage: ScanBasePackage,
    ): ScanClasses {
        val classes = mutableListOf<ScanClass>()
        if (!directory.exists()) return ScanClasses(emptyList())

        directory.listFiles()?.forEach { file ->
            when {
                file.isDirectory -> {
                    val subPackage = scanBasePackage.resolve(file.name)
                    val subScan = findClassesInDirectory(file, subPackage)
                    classes.addAll(subScan())
                }
                file.name.endsWith(".class") -> {
                    val className = file.name.substring(0, file.name.length - 6)
                    try {
                        val fqcn = "$scanBasePackage.$className"
                        val clazz = Class.forName(fqcn)
                        classes.add(ScanClass(clazz.kotlin))
                    } catch (_: ClassNotFoundException) {
                        // クラスが見つからない場合は無視
                    }
                }
            }
        }
        return ScanClasses(classes)
    }
}
