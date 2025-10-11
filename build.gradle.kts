plugins {
    alias(libs.plugins.spotless)

    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.mlutiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.plugin.spring) apply false

    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management) apply false
}

spotless {
    kotlin {
        ktlint()
        target("**/*.kt")
        targetExclude("build/**/*.kt", "bin/**/*.kt")
    }

    kotlinGradle {
        ktlint()
        target("**/*.kts")
        targetExclude("build/**/*.kts", "bin/**/*.kts")
    }
}
