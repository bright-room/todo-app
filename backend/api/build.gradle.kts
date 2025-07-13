plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.ktor)
}

group = "net.brightroom"
version = "1.0.0"
application {
    mainClass.set("net.brightroom.todo.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.shared)

    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.ktor.common)
    implementation(libs.bundles.ktor.server)

    testImplementation(libs.bundles.ktor.server.test)
}
