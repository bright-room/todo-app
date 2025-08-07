plugins {
    alias(libs.plugins.spotless)
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

    yaml {
        jackson()
        target("**/*.yaml", "**/*.yml")
    }
}
