import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.mlutiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm {
        kotlin {
            jvmToolchain(21)
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        jvmMain.dependencies {}
        jvmTest.dependencies {}

        wasmJsMain.dependencies {}
        wasmJsTest.dependencies {}
    }
}
