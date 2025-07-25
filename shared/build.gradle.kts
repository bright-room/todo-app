import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.mlutiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                devServer =
                    (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                        port = 3000
                        static =
                            (static ?: mutableListOf()).apply {
                                // Serve sources to debug inside the browser
                                add(rootDirPath)
                                add(projectDirPath)
                            }
                    }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.kotlinx)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        jvmMain.dependencies {}

        jvmTest.dependencies {
            implementation(libs.kotlin.test.junit)
        }

        wasmJsMain.dependencies {
            implementation(npm("@js-joda/timezone", "2.3.0"))
        }

        wasmJsTest.dependencies {}
    }
}
