plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.exposed.core)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit5)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}
