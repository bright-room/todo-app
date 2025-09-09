plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.exposed.core)

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
