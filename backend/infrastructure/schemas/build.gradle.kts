plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.backend.domain)
    implementation(projects.backend.infrastructure.migration.annotation)

    implementation(libs.exposed.core)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.exposed.r2dbc)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
