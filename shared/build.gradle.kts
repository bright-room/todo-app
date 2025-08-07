import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.mlutiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {}

        jvmMain.dependencies {}
        jvmTest.dependencies {}

        wasmJsMain.dependencies {
            implementation(npm("@js-joda/timezone", "2.3.0"))
        }
        wasmJsTest.dependencies {}
    }
}
