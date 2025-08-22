plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.shared)

    implementation(libs.kotlinx.datetime)
    implementation(libs.yavi)
    implementation(libs.password4j)

    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.junit.jupiter)
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
