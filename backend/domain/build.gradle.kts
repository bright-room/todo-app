plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.datetime)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
