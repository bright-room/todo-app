plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.exposed.core)

    annotationProcessor(libs.spring.boot.configuration.processor)

    implementation(kotlin("reflect"))

    testImplementation(libs.kotlin.test.junit5)
}

kotlin {
    jvmToolchain(21)
}

tasks {
    test {
        useJUnitPlatform()
    }
}
