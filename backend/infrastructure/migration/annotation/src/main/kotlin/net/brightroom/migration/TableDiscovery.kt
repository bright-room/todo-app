package net.brightroom.migration

import org.jetbrains.exposed.v1.core.Table
import java.io.File
import java.net.JarURLConnection
import java.net.URL
import kotlin.reflect.full.isSubclassOf

object TableDiscovery {
    fun findAnnotatedTableObjects(packageName: String): Array<Table> =
        findAllClassesInPackage(packageName)
            .filter { clazz ->
                runCatching { clazz.isAnnotationPresent(Migratable::class.java) && clazz.kotlin.isSubclassOf(Table::class) }
                    .getOrDefault(false)
            }.mapNotNull {
                runCatching {
                    val instance = getObjectInstance(it) as Table
                    val order = getTableOrder(it)
                    OrderedTable(instance, order)
                }.getOrDefault(null)
            }.sorted()
            .map { it.table }
            .toTypedArray()

    private fun getTableOrder(clazz: Class<*>): Int =
        runCatching {
            clazz.getAnnotation(Migratable::class.java)?.order ?: 0
        }.getOrDefault(0)

    private fun findAllClassesInPackage(packageName: String): List<Class<*>> {
        val classLoader = Thread.currentThread().contextClassLoader
        val path = packageName.replace('.', '/')
        val resources = classLoader.getResources(path)
        val classes = mutableListOf<Class<*>>()

        while (resources.hasMoreElements()) {
            val resource = resources.nextElement()
            when (resource.protocol) {
                "file" -> {
                    // ファイルシステムから検索
                    classes.addAll(findClassesInDirectory(File(resource.file), packageName))
                }
                "jar" -> {
                    // JARファイルから検索
                    classes.addAll(findClassesInJar(resource, packageName))
                }
            }
        }
        return classes
    }

    private fun findClassesInDirectory(
        directory: File,
        packageName: String,
    ): List<Class<*>> {
        val classes = mutableListOf<Class<*>>()
        if (!directory.exists()) return classes

        directory.listFiles()?.forEach { file ->
            when {
                file.isDirectory -> {
                    classes.addAll(
                        findClassesInDirectory(file, "$packageName.${file.name}"),
                    )
                }
                file.name.endsWith(".class") -> {
                    val className = file.name.substring(0, file.name.length - 6)
                    try {
                        val clazz = Class.forName("$packageName.$className")
                        classes.add(clazz)
                    } catch (e: ClassNotFoundException) {
                        // クラスが見つからない場合は無視
                    }
                }
            }
        }
        return classes
    }

    private fun findClassesInJar(
        resource: URL,
        packageName: String,
    ): List<Class<*>> {
        val classes = mutableListOf<Class<*>>()
        val jarConnection = resource.openConnection() as JarURLConnection
        val jarFile = jarConnection.jarFile
        val entries = jarFile.entries()
        val path = packageName.replace('.', '/')

        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            if (entry.name.startsWith(path) && entry.name.endsWith(".class")) {
                val className =
                    entry.name
                        .substring(0, entry.name.length - 6)
                        .replace('/', '.')

                try {
                    val clazz = Class.forName(className)
                    classes.add(clazz)
                } catch (e: ClassNotFoundException) {
                    // クラスが見つからない場合は無視
                }
            }
        }
        return classes
    }

    private fun getObjectInstance(clazz: Class<*>): Any =
        runCatching {
            val instanceField = clazz.getDeclaredField("INSTANCE") // Kotlinのobjectクラスの場合はINSTANCEフィールドから取得
            instanceField.isAccessible = true
            instanceField.get(null)
        }.recoverCatching {
            clazz.getDeclaredConstructor().newInstance() // INSTANCEフィールドが無い場合は通常のインスタンス化を試行
        }.getOrThrow()
}
