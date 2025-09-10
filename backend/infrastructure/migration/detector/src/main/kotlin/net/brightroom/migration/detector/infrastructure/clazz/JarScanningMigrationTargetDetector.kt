package net.brightroom.migration.detector.infrastructure.clazz

import net.brightroom.migration.detector.application.repository.clazz.MigrationTargetDetectRepository
import net.brightroom.migration.detector.domain.model.clazz.ScanBasePackage
import net.brightroom.migration.detector.domain.model.clazz.ScanClass
import net.brightroom.migration.detector.domain.model.clazz.ScanClasses
import org.springframework.stereotype.Repository
import java.net.JarURLConnection

@Repository
internal class JarScanningMigrationTargetDetector : MigrationTargetDetectRepository {
    override fun listAll(scanBasePackage: ScanBasePackage): ScanClasses {
        val classLoader = Thread.currentThread().contextClassLoader
        val path = scanBasePackage.toPath()
        val resources = classLoader.getResources(path)

        val classes = mutableListOf<ScanClass>()

        while (resources.hasMoreElements()) {
            val resource = resources.nextElement()
            if (resource.protocol == "jar") {
                val scanClasses = findClassesInJar(resource.openConnection() as JarURLConnection, scanBasePackage)
                classes.addAll(scanClasses())
            }
        }
        return ScanClasses(classes)
    }

    private fun findClassesInJar(
        jarConnection: JarURLConnection,
        scanBasePackage: ScanBasePackage,
    ): ScanClasses {
        val classes = mutableListOf<ScanClass>()
        val jarFile = jarConnection.jarFile
        val entries = jarFile.entries()

        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            if (entry.name.startsWith(scanBasePackage.toPath()) && entry.name.endsWith(".class")) {
                val className = entry.name.removeSuffix(".class").replace('/', '.')
                try {
                    val clazz = Class.forName(className)
                    classes.add(ScanClass(clazz.kotlin))
                } catch (_: ClassNotFoundException) {
                    // クラスが見つからない場合は無視
                }
            }
        }
        return ScanClasses(classes)
    }
}
