plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation(projects.shared)
    implementation(projects.backend.infrastructure.schemas)
    implementation(projects.backend.infrastructure.migration.detector)

    implementation(libs.spring.boot.starter)
    annotationProcessor(libs.spring.boot.configuration.processor)

    implementation(libs.kotlinx.datetime)

    implementation(libs.exposed.spring.boot.starter)
    implementation(libs.exposed.migration)

    implementation(libs.flyway)
    implementation(libs.flyway.database.postgresql)

    runtimeOnly(libs.jdbc.postgresql)
}

kotlin {
    jvmToolchain(21)
}

tasks {
    processTestResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}
