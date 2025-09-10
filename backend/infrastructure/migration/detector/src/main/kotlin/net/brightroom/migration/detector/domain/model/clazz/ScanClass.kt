package net.brightroom.migration.detector.domain.model.clazz

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@JvmInline
internal value class ScanClass(
    private val value: KClass<*>,
) {
    fun hasAnnotation(target: KClass<out Annotation>): Boolean =
        runCatching {
            val annotations = value.annotations
            annotations.any { it.annotationClass == target }
        }.getOrDefault(false)

    fun hasSuperclass(target: KClass<*>): Boolean =
        runCatching {
            value.isSubclassOf(target)
        }.getOrDefault(false)

    @Suppress("UNCHECKED_CAST")
    fun <T> toInstance(): T =
        runCatching {
            value.objectInstance as T ?: run {
                val instanceField = value.java.getDeclaredField("INSTANCE")
                instanceField.isAccessible = true
                instanceField.get(null) as T
            }
        }.recoverCatching {
            val constructor = value.java.getDeclaredConstructor()
            constructor.isAccessible = true
            constructor.newInstance() as T
        }.getOrThrow()

    override fun toString(): String = value.qualifiedName ?: ""
}
