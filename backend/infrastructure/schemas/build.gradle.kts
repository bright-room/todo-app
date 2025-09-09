plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.backend.domain)
    implementation(projects.backend.infrastructure.migration.detector)

    implementation(libs.exposed.core)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.exposed.r2dbc)
}

kotlin {
    jvmToolchain(21)
}
