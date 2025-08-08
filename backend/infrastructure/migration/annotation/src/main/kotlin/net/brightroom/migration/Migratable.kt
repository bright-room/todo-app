package net.brightroom.migration

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Migratable(
    val order: Int = 0,
)
